package com.solvd.scheduler.algorithm;

import com.solvd.scheduler.model.Group;
import com.solvd.scheduler.model.Lesson;

import java.util.Collections;
import java.util.List;

public class ScheduleGenerator implements IScheduleGenerate {
    private final ChromosomeInput chromosomeInput;
    private static final int MAX_GENERATIONS = 1000;
    private static final int POPULATION_SIZE = 100;

    public ScheduleGenerator(List<Group> groups, int numDays, int minLessons, int maxLessons) {
        this.chromosomeInput = new ChromosomeInput(groups, numDays, minLessons, maxLessons);
    }

    public List<Lesson> compute() {
        int generation = 1;

        Population population = new Population(POPULATION_SIZE, this.chromosomeInput);
        population.evaluate();

        printGenerationInfo(generation, population);

        while (generation < MAX_GENERATIONS &&
                (population.getIndividual(0).getMandatoryFitness() < 1 || population.getIndividual(0).getOptionalFitness() < 1)) {
            generation++;

            population.crossover();
            population.mutate();
            population.evaluate();

            if (population.getIndividual(0).getMandatoryFitness() == 1) {
                population.addGoodIndividual(population.getIndividual(0));
            }

            printGenerationInfo(generation, population);
        }

        Individual resultIndividual;

        if (population.getIndividual(0).getOptionalFitness() < 1) {
            System.out.println("NOT POSSIBLE TO CREATE SCHEDULE WITHOUT GAPS.");
            if (population.getIndividualsWithMandatoryFitnessSatisfied().size() == 0) {
                System.out.println("NOT POSSIBLE TO CREATE SCHEDULE WITH GAPS EITHER.");
                return null;
            }
            Collections.sort(population.getIndividualsWithMandatoryFitnessSatisfied());
            resultIndividual = population.getIndividualsWithMandatoryFitnessSatisfied().get(0);

        } else {
            resultIndividual = population.getIndividual(0);
        }

        System.out.println("Final solution:");
        System.out.println("-> Mandatory Fitness: " + resultIndividual.getMandatoryFitness());
        System.out.println("-> Optional Fitness: " + resultIndividual.getOptionalFitness());
        System.out.println("-> Conflicts: " + resultIndividual.calculateConflicts());
        System.out.println("-> Gaps in schedule for groups: " + resultIndividual.calculateGapsForGroups());
        System.out.println("-> Gaps in schedule for teachers: " + resultIndividual.calculateGapsForTeachers());

        return resultIndividual.getChromosome();
    }

    private void printGenerationInfo(int generation, Population population) {
        System.out.println("Generation -> Mandatory - Optional: " + generation + " -> " +
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