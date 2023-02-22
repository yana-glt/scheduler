package com.solvd.scheduler.algorithm;

import com.solvd.scheduler.model.Group;
import com.solvd.scheduler.model.Lesson;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Collections;
import java.util.List;

public class ScheduleGenerator implements IScheduleGenerate {
    private final ChromosomeInput chromosomeInput;
    private static final int MAX_GENERATIONS = 1000;
    private static final int POPULATION_SIZE = 100;
    private final static Logger logger = LogManager.getLogger(ScheduleGenerator.class);

    public ScheduleGenerator(List<Group> groups, int numDays, int minLessons, int maxLessons) {
        this.chromosomeInput = new ChromosomeInput(groups, numDays, minLessons, maxLessons);
    }

    public List<Lesson> compute() {

        if (chromosomeInput.getGroups().size() > 0) {
            Integer totalAmountOfLessons = chromosomeInput.getGroups()
                    .stream()
                    .map(p -> p.getSubjectsAsList().size())
                    .reduce(0, Integer::sum);

            if (totalAmountOfLessons > 0) {

                Population population = new Population(POPULATION_SIZE, this.chromosomeInput);

                System.out.println("=============  Generation of schedule started - please wait  =============");
                int generation = 1;

                population.evaluate();

                if (population.getIndividual(0).getMandatoryFitness() == 1) {
                    population.addGoodIndividual(population.getIndividual(0));
                }
                logGenerationInfo(generation, population);

                while (generation < MAX_GENERATIONS &&
                        (population.getIndividual(0).getMandatoryFitness() < 1 || population.getIndividual(0).getOptionalFitness() < 1)) {
                    generation++;

                    population.crossover();
                    population.mutate();
                    population.evaluate();

                    if (population.getIndividual(0).getMandatoryFitness() == 1) {
                        population.addGoodIndividual(population.getIndividual(0));
                    }

                    logGenerationInfo(generation, population);
                }

                if (population.getIndividualsWithMandatoryFitnessSatisfied().size() == 0) {
                    System.out.println("Sorry, schedule satisfying mandatory requirements can't be generated. " +
                            "Please check input info and number of lessons in DB");
                    logger.info("Not possible to create a schedule satisfying mandatory requirements.");
                    return null;
                } else {
                    Collections.sort(population.getIndividualsWithMandatoryFitnessSatisfied());
                    logger.debug("List of individuals with mandatory fitness satisfied sorted based on optional fitness");
                    Individual resultIndividual = population.getIndividualsWithMandatoryFitnessSatisfied().get(0);
                    logger.info(String.format("Chosen individual[%d] with mandatory fitness satisfied and best optional fitness [%.19f]",
                            resultIndividual.getIndividualId(), resultIndividual.getOptionalFitness()));

                    logger.info(String.format("Schedule created [mandatory fitness: %.1f and optional fitness: %.19f] [individual id: %d]",
                            resultIndividual.getMandatoryFitness(), resultIndividual.getOptionalFitness(), resultIndividual.getIndividualId()));

                    System.out.println("Final solution:");
                    System.out.println("-> Mandatory Fitness: " + resultIndividual.getMandatoryFitness());
                    System.out.println("-> Optional Fitness: " + resultIndividual.getOptionalFitness());
                    System.out.println("-> Conflicts: " + resultIndividual.calculateConflicts());
                    System.out.println("-> Gaps in schedule for groups: " + resultIndividual.calculateGapsForGroups());
                    System.out.println("-> Gaps in schedule for teachers: " + resultIndividual.calculateGapsForTeachers());

                    return resultIndividual.getChromosome();
                }
            } else {
                logger.info("Schedule not generated - no subjects to allocate");
                System.out.println("Schedule can't be generated. No subjects to allocate. " +
                        "Please check if groups have subject assigned in DB.");
                return null;
            }
        } else {
            logger.info("Schedule not generated - no groups available");
            System.out.println("Schedule can't be generated. No groups available. " +
                    "Please check if have any groups in DB.");
            return null;
        }

    }

    private void logGenerationInfo(int generation, Population population) {
        logger.info("Generation -> Mandatory - Optional: " + generation + " -> " +
                population.getIndividual(0).getMandatoryFitness() + " - "
                + population.getIndividual(0).getOptionalFitness());
    }
}