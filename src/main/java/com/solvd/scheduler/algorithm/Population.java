package com.solvd.scheduler.algorithm;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Population {
    ChromosomeInput chromosomeInput;
    private List<Individual> population;
    private final List<Individual> individualsWithMandatoryFitnessSatisfied;

    public Population(int populationSize, ChromosomeInput chromosomeInput) {
        this.chromosomeInput = chromosomeInput;
        this.population = new ArrayList<>();
        for (int i = 0; i < populationSize; i++) {
            this.population.add(new Individual(chromosomeInput));
        }
        individualsWithMandatoryFitnessSatisfied = new ArrayList<>();
    }

    public Individual getIndividual(int offset) {
        return population.get(offset);
    }

    public List<Individual> getIndividualsWithMandatoryFitnessSatisfied() {
        return individualsWithMandatoryFitnessSatisfied;
    }

    public void addGoodIndividual(Individual goodEnoughOne){
        individualsWithMandatoryFitnessSatisfied.add(goodEnoughOne);
    }

    public void evaluate() {
        for (Individual individual : this.population) {
            individual.calculateMandatoryFitness();
            individual.calculateOptionalFitness();
        }
        Collections.sort(this.population);
    }

    public void mutate() {
        List<Individual> newPopulation = new ArrayList<>();
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
    }

    public void crossover() {
        List<Individual> newPopulation = new ArrayList<>();
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
    }

    private Individual parentTournamentSelection() {
        List<Individual> tournamentList = new ArrayList<>();

        for (int i = 0; i < 5; i++) {
            tournamentList.add(this.population.get((int) (this.population.size() * Math.random())));
        }
        Collections.sort(tournamentList);
        return tournamentList.get(0);
    }
}