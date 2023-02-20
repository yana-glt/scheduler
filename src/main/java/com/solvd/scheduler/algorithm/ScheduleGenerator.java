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
        System.out.println("=============  Generation of schedule started - please wait  =============");
        int generation = 1;

        Population population = new Population(POPULATION_SIZE, this.chromosomeInput);
        population.evaluate();
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

        Individual resultIndividual;

        if (population.getIndividual(0).getOptionalFitness() < 1) {
            System.out.println("NOT POSSIBLE TO CREATE SCHEDULE WITHOUT GAPS.");
            logger.info("Not possible to create a schedule satisfying optional requirements.");
            if (population.getIndividualsWithMandatoryFitnessSatisfied().size() == 0) {
                System.out.println("NOT POSSIBLE TO CREATE SCHEDULE WITH GAPS AND EQUAL DISTRIBUTION OF SUBJECTS EITHER.");
                logger.info("Not possible to create a schedule satisfying mandatory requirements.");
                return null;
            }
            Collections.sort(population.getIndividualsWithMandatoryFitnessSatisfied());
            logger.debug("List of individuals with mandatory fitness satisfied sorted based on optional fitness");
            resultIndividual = population.getIndividualsWithMandatoryFitnessSatisfied().get(0);
            logger.info(String.format("Chosen individual[%d] with mandatory fitness satisfied and best optional fitness [%.19f]",
                    resultIndividual.getIndividualId(), resultIndividual.getOptionalFitness()));

        } else {
            resultIndividual = population.getIndividual(0);
            logger.info(String.format("Chosen individual[%d] with both mandatory and optional fitness satisfied",
                    resultIndividual.getIndividualId()));
        }
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

    private void logGenerationInfo(int generation, Population population) {
        logger.info("Generation -> Mandatory - Optional: " + generation + " -> " +
                population.getIndividual(0).getMandatoryFitness() + " - "
                + population.getIndividual(0).getOptionalFitness());
    }

    public static void print(List<Lesson> lessons) {
        if (lessons != null) {
            Collections.sort(lessons);
            for (Lesson lesson : lessons) {
                System.out.println(lesson);
            }
        }
    }
}