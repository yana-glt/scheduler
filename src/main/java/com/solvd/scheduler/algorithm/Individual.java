package com.solvd.scheduler.algorithm;

import com.solvd.scheduler.model.*;

import java.util.*;

public class Individual implements Comparable<Individual> {
    private final List<Lesson> chromosome;
    private double mandatoryFitness = -1;
    private double optionalFitness = -1;

    public Individual(ChromosomeInput chromosomeInput) {
        chromosome = new ArrayList<>();
        int id = 0;
        for (Group group : chromosomeInput.getGroups()) {
            for (Subject sub : group.getSubjectsAsList()) {
                chromosome.add(new Lesson(id, sub, group, chromosomeInput.getRandomTimeslot()));
                id++;
            }
        }
    }

    public Individual(List<Lesson> chromosome) {
        this.chromosome = chromosome;
        this.calculateMandatoryFitness();
        this.calculateOptionalFitness();
    }

    public List<Lesson> getChromosome() {
        return this.chromosome;
    }

    public int getChromosomeLength() {
        return this.chromosome.size();
    }

    public void setGene(int offset, Timeslot timeslot) {
        this.chromosome.get(offset).setTimeslot(timeslot);
    }

    public Timeslot getGene(int offset) {
        return this.chromosome.get(offset).getTimeslot();
    }

    public double getMandatoryFitness() {
        return this.mandatoryFitness;
    }

    public double getOptionalFitness() {
        return optionalFitness;
    }

    public int calculateGapsForGroups() {
        int gap = 0;

        if (this.chromosome.size() > 1) {
            Weekday[] days = Arrays.copyOfRange(Weekday.values(), 0, ChromosomeInput.numSchoolDays);
            List<Long> groupIds = chromosome
                    .stream()
                    .map(p -> p.getGroup().getId())
                    .distinct()
                    .toList();

            for (long id : groupIds) {
                List<Lesson> lessons = chromosome
                        .stream()
                        .filter(p -> p.getGroup().getId() == id)
                        .toList();

                for (Weekday day : days) {
                    List<Lesson> lessonsOnDaySorted = lessons.stream().filter(p -> p.getTimeslot().getDay().equals(day))
                            .sorted().toList();
                    if (lessonsOnDaySorted.size() > 1) {
                        for (int i = 0; i < lessonsOnDaySorted.size() - 1; i++) {
                            int difference = lessonsOnDaySorted.get(i + 1).getTimeslot().getSlot() - lessonsOnDaySorted.get(i).getTimeslot().getSlot();
                            if (difference > 1) {
                                gap++;
                            }
                        }
                    }
                }
            }
        }
        return gap;
    }

    public int calculateGapsForTeachers() {
        int gap = 0;

        if (this.chromosome.size() > 1) {
            Weekday[] days = Arrays.copyOfRange(Weekday.values(), 0, ChromosomeInput.numSchoolDays);
            List<Long> subjectIds = chromosome
                    .stream()
                    .map(p -> p.getSubject().getId())
                    .distinct()
                    .toList();

            for (long id : subjectIds) {
                List<Lesson> lessons = chromosome
                        .stream()
                        .filter(p -> p.getSubject().getId() == id)
                        .toList();

                for (Weekday day : days) {
                    List<Lesson> lessonsOnDaySorted = lessons
                            .stream()
                            .filter(p -> p.getTimeslot().getDay().equals(day))
                            .sorted(Comparator.comparing(o -> (o.getTimeslot().getSlot())))
                            .toList();

                    if (lessonsOnDaySorted.size() > 1) {
                        for (int i = 0; i < lessonsOnDaySorted.size() - 1; i++) {
                            int difference = lessonsOnDaySorted.get(i + 1).getTimeslot().getSlot() - lessonsOnDaySorted.get(i).getTimeslot().getSlot();
                            if (difference > 1) {
                                gap++;
                            }
                        }
                    }
                }
            }
        }
        return gap;
    }

    public void calculateMandatoryFitness() {
        this.mandatoryFitness = 1 / (double) (this.calculateConflicts() + 1);
    }

    public void calculateOptionalFitness() {
        this.optionalFitness = 1 / (double) (this.calculateGapsForGroups() + calculateGapsForTeachers() + 1);
    }

    public int calculateConflicts() {
        int conflict = 0;

        if (this.chromosome.size() > 1) {
            for (Lesson l1 : chromosome) {
                for (Lesson l2 : chromosome) {
                    if (l1.getSubject().getId() == l2.getSubject().getId()
                            && l1.getTimeslot().getId() == l2.getTimeslot().getId()
                            && l1.getLessonId() != l2.getLessonId()) {
                        conflict++;
                    }
                }

                for (Lesson l2 : chromosome) {
                    if (l1.getGroup().getId() == l2.getGroup().getId()
                            && l1.getTimeslot().getId() == l2.getTimeslot().getId()
                            && l1.getLessonId() != l2.getLessonId()) {
                        conflict++;
                    }
                }
            }

            Weekday[] days = Arrays.copyOfRange(Weekday.values(), 0, ChromosomeInput.numSchoolDays);
            List<Long> groupIds = chromosome
                    .stream()
                    .map(p -> p.getGroup().getId())
                    .distinct()
                    .toList();

            for (long id : groupIds) {
                List<Lesson> lessons = chromosome
                        .stream()
                        .filter(p -> p.getGroup().getId() == id)
                        .toList();

                for (Weekday day : days) {
                    int numLessonsPerDay = (int) lessons
                            .stream()
                            .filter(p -> p.getTimeslot().getDay().equals(day))
                            .count();

                    if (numLessonsPerDay < ChromosomeInput.minLessons) {
                        conflict++;
                    }

                    if (numLessonsPerDay > ChromosomeInput.maxLessons) {
                        conflict++;
                    }
                }
            }

            for (long groupId : groupIds) {
                Group group = chromosome
                        .stream()
                        .filter(p -> p.getGroup().getId() == groupId)
                        .distinct()
                        .map(Lesson::getGroup)
                        .toList()
                        .get(0);

                List<Lesson> lessonsForGivenGroup = chromosome
                        .stream()
                        .filter(p -> p.getGroup().getId() == groupId)
                        .toList();

                for (Map.Entry<Subject, Integer> entry : group.getSubjectAmountPerWeek().entrySet()) {
                    Subject subject = entry.getKey();
                    int hoursOfSubject = entry.getValue();

                    if (hoursOfSubject <= ChromosomeInput.numSchoolDays) {
                        for (Weekday day : days) {
                            int numHoursOfSubjectOnGivenDay = (int) lessonsForGivenGroup
                                    .stream()
                                    .filter(p -> p.getSubject().equals(subject))
                                    .filter(p -> p.getTimeslot().getDay().equals(day))
                                    .count();
                            if (numHoursOfSubjectOnGivenDay > 1) {
                                conflict++;
                            }
                        }
                    } else {
                        int minHours = (hoursOfSubject / ChromosomeInput.numSchoolDays);
                        int maxHours = minHours + 1;
                        for (Weekday day : days) {
                            int numHoursOfSubjectOnGivenDay = (int) lessonsForGivenGroup.stream()
                                    .filter(p -> p.getSubject().equals(subject))
                                    .filter(p -> p.getTimeslot().getDay().equals(day))
                                    .count();
                            if (numHoursOfSubjectOnGivenDay < minHours || numHoursOfSubjectOnGivenDay > maxHours) {
                                conflict++;
                            }
                        }
                    }
                }
            }
        }
        return conflict;
    }

    @Override
    public int compareTo(Individual o) {
        int compareMandatory = Double.compare(o.getMandatoryFitness(), this.getMandatoryFitness());
        if (compareMandatory == 0) {
            return Double.compare(o.getOptionalFitness(), this.getOptionalFitness());
        } else {
            return compareMandatory;
        }
    }
}