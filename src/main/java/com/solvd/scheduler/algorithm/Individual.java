package com.solvd.scheduler.algorithm;

import com.solvd.scheduler.model.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.*;
import java.util.stream.Collectors;

public class Individual implements Comparable<Individual> {
    private int individualId;
    private final List<Lesson> chromosome;
    private double mandatoryFitness = -1;
    private double optionalFitness = -1;
    private static int count = 1;
    private final static Logger LOGGER = LogManager.getLogger(Individual.class);

    public Individual(ChromosomeInput chromosomeInput) {
        individualId = count++;
        chromosome = new ArrayList<>();
        int id = 0;
        for (Group group : chromosomeInput.getGroups()) {
            for (Subject sub : group.getSubjectsAsList()) {
                chromosome.add(new Lesson(id, sub, group, chromosomeInput.getRandomTimeslot()));
                id++;
            }
        }
        LOGGER.trace(String.format("Created a individual [%d] with %d lessons", individualId, chromosome.size()));
    }

    public Individual(List<Lesson> chromosome) {
        this.chromosome = chromosome;
        this.calculateMandatoryFitness();
        this.calculateOptionalFitness();
    }

    public int getIndividualId() {
        return individualId;
    }

    public List<Lesson> getChromosome() {
        return this.chromosome;
    }

    public int getChromosomeLength() {
        return this.chromosome.size();
    }

    public void setGene(int offset, Timeslot timeslot) {
        Lesson lesson = this.chromosome.get(offset);
        lesson.setTimeslot(timeslot);
        LOGGER.trace(String.format("[individual %d] set %s [lesson id: %d group: %s subject: %s]",
                individualId, timeslot, lesson.getLessonId(), lesson.getGroup().getName(), lesson.getSubject().getName()));
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

    public void calculateMandatoryFitness() {
        this.mandatoryFitness = 1 / (double) (this.calculateConflicts() + 1);
        LOGGER.debug(String.format("[individual %d] calculated mandatory fitness: %f",
                individualId, this.mandatoryFitness));
    }

    public void calculateOptionalFitness() {
        this.optionalFitness = 1 / (double) (this.calculateGapsForGroups() + calculateGapsForTeachers() + 1);
        LOGGER.debug(String.format("[individual %d] calculated optional fitness: %f",
                individualId, this.optionalFitness));
    }

    public int calculateGapsForGroups() {
        int gap = 0;
        if (this.chromosome.size() > 1) {
            Weekday[] days = Arrays.copyOfRange(Weekday.values(), 0, ChromosomeInput.numSchoolDays);
            List<Long> groupIds = chromosome
                    .stream()
                    .map(p -> p.getGroup().getId())
                    .distinct()
                    .collect(Collectors.toList());
            for (long id : groupIds) {
                List<Lesson> lessons = chromosome
                        .stream()
                        .filter(p -> p.getGroup().getId() == id)
                        .collect(Collectors.toList());
                for (Weekday day : days) {
                    List<Lesson> lessonsOnDaySorted = lessons
                            .stream()
                            .filter(p -> p.getTimeslot().getDay().equals(day))
                            .sorted()
                            .collect(Collectors.toList());
                    if (lessonsOnDaySorted.size() > 1) {
                        for (int i = 0; i < lessonsOnDaySorted.size() - 1; i++) {
                            int difference = lessonsOnDaySorted.get(i + 1).getTimeslot().getSlot() - lessonsOnDaySorted.get(i).getTimeslot().getSlot();
                            if (difference > 1) {
                                gap++;
                                LOGGER.trace(String.format("GAP [individual %d] for groups between: [%s] and [%s]",
                                        individualId, lessonsOnDaySorted.get(i), lessonsOnDaySorted.get(i + 1)));
                            }
                        }
                    }
                }
            }
        }
        LOGGER.debug(String.format("[individual %d] calculated total number of gaps for groups: %d", individualId, gap));
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
                    .collect(Collectors.toList());
            for (long id : subjectIds) {
                List<Lesson> lessons = chromosome
                        .stream()
                        .filter(p -> p.getSubject().getId() == id)
                        .collect(Collectors.toList());
                for (Weekday day : days) {
                    List<Lesson> lessonsOnDaySorted = lessons
                            .stream()
                            .filter(p -> p.getTimeslot().getDay().equals(day))
                            .sorted(Comparator.comparing(o -> (o.getTimeslot().getSlot())))
                            .collect(Collectors.toList());
                    if (lessonsOnDaySorted.size() > 1) {
                        for (int i = 0; i < lessonsOnDaySorted.size() - 1; i++) {
                            int difference = lessonsOnDaySorted.get(i + 1).getTimeslot().getSlot() - lessonsOnDaySorted.get(i).getTimeslot().getSlot();
                            if (difference > 1) {
                                gap++;
                                LOGGER.trace(String.format("GAP [individual %d] for teachers between: [%s] and [%s]",
                                        individualId, lessonsOnDaySorted.get(i), lessonsOnDaySorted.get(i + 1)));
                            }
                        }
                    }
                }
            }
        }
        LOGGER.debug(String.format("[individual %d] calculated total number of gaps for teachers: %d", individualId, gap));
        return gap;
    }

    public int calculateConflicts() {
        int conflict = 0;
        Weekday[] days = Arrays.copyOfRange(Weekday.values(), 0, ChromosomeInput.numSchoolDays);

        if (this.chromosome.size() > 1) {
            conflict = calculateTimeslotOverlapConflicts(this.chromosome) +
                    calculateNumLessonsPerDayConstrainsConflicts(days, this.chromosome) +
                    calculatedSubjectDistributionConflicts(days, this.chromosome);
        }
        LOGGER.debug(String.format("[individual %d] calculated total number of conflicts for groups + teachers: %d",
                individualId, conflict));
        return conflict;
    }

    private int calculateTimeslotOverlapConflicts(List<Lesson> chromosome) {
        int conflict = 0;
        for (Lesson l1 : chromosome) {
            for (Lesson l2 : chromosome) {
                if (l1.getSubject().getId() == l2.getSubject().getId()
                        && l1.getTimeslot().getId() == l2.getTimeslot().getId()
                        && l1.getLessonId() != l2.getLessonId()) {
                    conflict++;
                    LOGGER.trace(String.format("CONFLICT [individual id: %d] time overlap [subjects] between: [%s] and [%s]",
                            individualId, l1, l2));
                }
            }

            for (Lesson l2 : chromosome) {
                if (l1.getGroup().getId() == l2.getGroup().getId()
                        && l1.getTimeslot().getId() == l2.getTimeslot().getId()
                        && l1.getLessonId() != l2.getLessonId()) {
                    conflict++;
                    LOGGER.trace(String.format("CONFLICT [individual %d] time overlap [groups] between: [%s] and [%s]",
                            individualId, l1, l2));
                }
            }
        }
        return conflict;
    }

    private int calculateNumLessonsPerDayConstrainsConflicts(Weekday[] days, List<Lesson> chromosome) {
        int conflict = 0;
        List<Long> groupIds = chromosome
                .stream()
                .map(p -> p.getGroup().getId())
                .distinct()
                .collect(Collectors.toList());
        for (long id : groupIds) {
            List<Lesson> lessons = chromosome
                    .stream()
                    .filter(p -> p.getGroup().getId() == id)
                    .collect(Collectors.toList());
            for (Weekday day : days) {
                int numLessonsPerDay = (int) lessons
                        .stream()
                        .filter(p -> p.getTimeslot().getDay().equals(day))
                        .count();
                if (numLessonsPerDay > 0) {
                    if (numLessonsPerDay < ChromosomeInput.minLessons) {
                        conflict++;
                        LOGGER.trace(String.format("CONFLICT [individual %d] Min num of lessons not satisfied for group[%d] " +
                                "on %s. Calculated num of hours: %d", individualId, id, day, numLessonsPerDay));
                    }
                    if (numLessonsPerDay > ChromosomeInput.maxLessons) {
                        conflict++;
                        LOGGER.trace(String.format("CONFLICT [individual %d] Max num of lessons not satisfied for group[%d] " +
                                "on %s. Calculated num of hours: %d", individualId, id, day, numLessonsPerDay));
                    }
                }
            }
        }
        return conflict;
    }

    private int calculatedSubjectDistributionConflicts(Weekday[] days, List<Lesson> chromosome) {
        int conflict = 0;
        List<Long> groupIds = chromosome
                .stream()
                .map(p -> p.getGroup().getId())
                .distinct()
                .collect(Collectors.toList());
        for (long groupId : groupIds) {
            Group group = chromosome
                    .stream()
                    .filter(p -> p.getGroup().getId() == groupId)
                    .distinct()
                    .map(Lesson::getGroup)
                    .collect(Collectors.toList())
                    .get(0);
            List<Lesson> lessonsForGivenGroup = chromosome
                    .stream()
                    .filter(p -> p.getGroup().getId() == groupId)
                    .collect(Collectors.toList());
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
                            LOGGER.trace(String.format("CONFLICT [individual %d] Subject distribution conflict: " +
                                            "too many lessons of %s for group[%d] on %s. Should be [0 - 1]. Calculated num" +
                                            " of lessons: %d",
                                    individualId, subject.getName(), groupId, day, numHoursOfSubjectOnGivenDay));
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
                            LOGGER.trace(String.format("CONFLICT [individual %d] Subject distribution conflict: " +
                                            "invalid number of lessons of %s for group[%d] on %s." +
                                            " Should be [%d - %d]. Calculated num of lessons: %d",
                                    individualId, subject.getName(), groupId, day, minHours, maxHours, numHoursOfSubjectOnGivenDay));
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
