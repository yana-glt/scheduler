package com.solvd.scheduler.algorithm;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Population {
    ChromosomeInput chromosomeInput;
    private List<Individual> population;
    private final List<Individual> individualsWithMandatoryFitnessSatisfied;
    private final static Logger LOGGER = LogManager.getLogger(Population.class);

    public Population(int populationSize, ChromosomeInput chromosomeInput) {
        this.chromosomeInput = chromosomeInput;
        this.population = new ArrayList<>();
        for (int i = 0; i < populationSize; i++) {
            this.population.add(new Individual(chromosomeInput));
        }
        LOGGER.info(String.format("Population init with %d individuals", populationSize));
        individualsWithMandatoryFitnessSatisfied = new ArrayList<>();
    }

    public Individual getIndividual(int offset) {
        return population.get(offset);
    }

    public List<Individual> getIndividualsWithMandatoryFitnessSatisfied() {
        return individualsWithMandatoryFitnessSatisfied;
    }

    public void addGoodIndividual(Individual goodEnoughOne) {
        individualsWithMandatoryFitnessSatisfied.add(goodEnoughOne);
        LOGGER.debug(String.format("Added individual [id: %d] with satisfied mandatory fitness to list [optional fitness: %f]",
                goodEnoughOne.getIndividualId(), goodEnoughOne.getOptionalFitness()));
    }

    public void evaluate() {
        LOGGER.debug("Evaluation of population started");
        for (Individual individual : this.population) {
            individual.calculateMandatoryFitness();
            individual.calculateOptionalFitness();
            LOGGER.trace(String.format("Individual [id: %d] m.fitness = %f opt.fitness = %f",
                    individual.getIndividualId(), individual.getMandatoryFitness(), individual.getOptionalFitness()));
        }
        LOGGER.debug("Evaluation of population ended");
        Collections.sort(this.population);
        LOGGER.debug("Population sorted");
    }

    public void mutate() {
        List<Individual> newPopulation = new ArrayList<>();
        LOGGER.debug("Mutation of population started");
        for (Individual individual : population) {
            Individual randomIndividual = new Individual(this.chromosomeInput);
            for (int gene = 0; gene < individual.getChromosomeLength(); gene++) {
                if (0.01 > Math.random()) {
                    individual.setGene(gene, randomIndividual.getGene(gene));
                }
            }
            newPopulation.add(individual);
        }
        this.population = newPopulation;
        LOGGER.debug("Mutation of population ended");
    }

    public void crossover() {
        List<Individual> newPopulation = new ArrayList<>();
        LOGGER.debug("Crossover of population started");
        for (Individual parent1 : population) {
            Individual parent2 = parentTournamentSelection();
            Individual offspring = new Individual(this.chromosomeInput);
            for (int gene = 0; gene < parent1.getChromosomeLength(); gene++) {
                if (0.5 > Math.random()) {
                    offspring.setGene(gene, parent1.getGene(gene));
                } else {
                    offspring.setGene(gene, parent2.getGene(gene));
                }
            }
            newPopulation.add(offspring);
        }
        this.population = newPopulation;
        LOGGER.debug("Crossover of population ended");
    }

    private Individual parentTournamentSelection() {
        List<Individual> tournamentList = new ArrayList<>();
        LOGGER.debug("Parent tournament of population started");
        for (int i = 0; i < 5; i++) {
            tournamentList.add(this.population.get((int) (this.population.size() * Math.random())));
        }
        Collections.sort(tournamentList);
        LOGGER.debug("List of parents selected in tournament sorted");
        Individual parent = tournamentList.get(0);
        LOGGER.debug(String.format("Parent tournament of population ended [individual %d chosen]", parent.getIndividualId()));
        return parent;
    }
}
