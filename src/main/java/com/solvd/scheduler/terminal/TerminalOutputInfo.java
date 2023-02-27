package com.solvd.scheduler.terminal;

import com.solvd.scheduler.algorithm.ChromosomeInput;
import com.solvd.scheduler.algorithm.Individual;
import com.solvd.scheduler.model.Group;
import com.solvd.scheduler.model.Lesson;
import com.solvd.scheduler.model.Subject;
import com.solvd.scheduler.model.Weekday;
import io.bretty.console.table.Alignment;
import io.bretty.console.table.ColumnFormatter;
import io.bretty.console.table.Table;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

public class TerminalOutputInfo {
    private final static Logger LOGGER = LogManager.getLogger(TerminalOutputInfo.class);

    public static void presentOutput(List<Lesson> lessons) {
        if (lessons != null && lessons.size() > 0) {
            Individual individual = new Individual(lessons);
            if (individual.getMandatoryFitness() == 1) {
                System.out.println("The schedule for all groups was drawn up. " +
                        "\nPlease enter :" +
                        "\n1 if you want to get the schedule for all groups" +
                        "\n2 if you want to get the schedule for all teachers" +
                        "\n3 if you want to get the schedule for all groups and teachers");
                chooseOption(individual);
            }
        } else {
            System.out.println("The schedule for all groups cannot be drawn up without conflicts. " +
                    "\nPlease try again by changing your input options or data in DB");
        }
    }

    public static void chooseOption(Individual ind) {
        try (Scanner scanner = new Scanner(System.in)) {
            if (scanner.hasNextLine()) {
                String input = String.valueOf(scanner.nextLine());
                switch (input) {
                    case ("1"):
                        LOGGER.debug("User has selected an option 'ALL GROUPS'");
                        System.out.println("This is the weekly schedule for the groups: ");
                        System.out.println(printScheduleForGroups(ind));
                        break;
                    case ("2"):
                        LOGGER.debug("User has selected an option 'ALL TEACHERS'");
                        System.out.println("This is the weekly schedule for the teachers: ");
                        System.out.println(printScheduleForTeachers(ind));
                        break;
                    case ("3"):
                        LOGGER.debug("User has selected an option 'ALL'");
                        System.out.println("This is the weekly schedule: ");
                        System.out.println(printScheduleForGroups(ind));
                        System.out.println(printScheduleForTeachers(ind));
                        break;
                    default:
                        LOGGER.debug(String.format("Incorrect user input: [%s] does not match the condition", input));
                        System.out.println("The entered data does not match the condition. Please try again");
                        presentOutput(ind.getChromosome());
                        break;
                }
            }
        }
    }

    public static String[] createTableHeader() {
        String[] tableHeaders = new String[]{"Lesson", Weekday.MONDAY.toString(),
                Weekday.TUESDAY.toString(), Weekday.WEDNESDAY.toString(), Weekday.THURSDAY.toString(),
                Weekday.FRIDAY.toString()};
        return tableHeaders;
    }

    public static String printScheduleForGroups(Individual ind) {
        StringBuilder outputString = new StringBuilder();
        int tableRows = ChromosomeInput.SCHOOL_WORKING_HOURS;
        int tableColumns = ChromosomeInput.MAX_SCHOOL_WORKING_DAYS + 1;
        List<Group> groups = ind.getChromosome().stream().map(Lesson::getGroup).distinct().collect(Collectors.toList());
        for (Group g : groups) {
            String[][] groupSchedule = new String[tableRows][tableColumns];
            for (String[] row : groupSchedule) {
                Arrays.fill(row, "");
            }
            for (int row = 0; row < tableRows; row++) {
                groupSchedule[row][0] = (row + ChromosomeInput.SCHOOL_WORKING_HOURS) +
                        ":00-" + (row + ChromosomeInput.SCHOOL_WORKING_HOURS + 1) + ":00";
            }
            List<Lesson> lessons = ind.getChromosome().stream().filter(p -> p.getGroup().getId() == g.getId()).collect(Collectors.toList());
            for (Lesson l : lessons) {
                groupSchedule[l.getTimeslot().getSlot() - 1][l.getTimeslot().getDay().ordinal() + 1] = l.getSubject().getName() +
                        " (" + l.getSubject().getTeacher().getName() + ")";
            }
            outputString.append(String.format("Group %s:%n%s%n%n", g.getName(),
                    Table.of(createTableHeader(), groupSchedule, ColumnFormatter.text(Alignment.CENTER, 26))));
        }
        return outputString.toString();
    }

    public static String printScheduleForTeachers(Individual ind) {
        StringBuilder outputString = new StringBuilder();
        int tableRows = ChromosomeInput.SCHOOL_WORKING_HOURS;
        int tableColumns = ChromosomeInput.MAX_SCHOOL_WORKING_DAYS + 1;
        List<Subject> subjects = ind.getChromosome().stream().map(Lesson::getSubject).distinct().collect(Collectors.toList());
        for (Subject s : subjects) {
            String[][] groupSchedule = new String[tableRows][tableColumns];
            for (String[] row : groupSchedule) {
                Arrays.fill(row, "");
            }
            for (int row = 0; row < tableRows; row++) {
                groupSchedule[row][0] = (row + ChromosomeInput.SCHOOL_WORKING_HOURS) +
                        ":00-" + (row + ChromosomeInput.SCHOOL_WORKING_HOURS + 1) + ":00";
            }
            List<Lesson> lessons = ind.getChromosome().stream().filter(p -> p.getSubject().getId() == s.getId()).collect(Collectors.toList());
            for (Lesson l : lessons) {
                groupSchedule[l.getTimeslot().getSlot() - 1][l.getTimeslot().getDay().ordinal() + 1] = l.getSubject().getName() +
                        " (" + l.getGroup().getName() + ")";
            }
            outputString.append(String.format("Subject %s:%s%n%s%n%n", s.getName(), s.getTeacher().getName(),
                    Table.of(createTableHeader(), groupSchedule, ColumnFormatter.text(Alignment.CENTER, 26))));
        }
        return outputString.toString();
    }
}
