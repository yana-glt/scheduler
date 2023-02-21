package com.solvd.scheduler.scanner;

import java.util.List;
import java.util.Scanner;

import com.solvd.scheduler.exception.InputException;
import com.solvd.scheduler.model.Group;

import static com.solvd.scheduler.Main.logger;
import static com.solvd.scheduler.scanner.MinPossibleNums.getMinAmountOfWorkingDaysPerWeek;
import static com.solvd.scheduler.scanner.MinPossibleNums.getMinPossibleLessonsPerDay;

public class TerminalInputInfo {
    public static final int MAX_NUM_OF_SLOTS_PER_DAY = 8;
    public static final int MAX_AMOUNT_OF_WORKING_DAYS_PER_WEEK = 6;
    public static int daysPerWeek;
    public static int maxLessonsPerDay;
    public static int minLessonsPerDay;
    private static int amountOfWorkingDaysPerAWeek;
    private static int minNumberOfLessonsPerDay;
    private static int maxNumberOfLessonsPerDay;

    public static void openScanner(List<Group> groups) throws InputException {
        daysPerWeek = getAmountOfWorkingDaysPerAWeek(groups);
        maxLessonsPerDay = getMaxNumberOfLessonsPerDay(groups);
        minLessonsPerDay = getMinNumberOfLessonsPerDay(groups);
    }

    public static int getAmountOfWorkingDaysPerAWeek(List<Group> groups) throws InputException {
        logger.info("The number of working days is requested");
        if (getMinAmountOfWorkingDaysPerWeek(groups) == MAX_AMOUNT_OF_WORKING_DAYS_PER_WEEK) {
            System.out.printf("\nSelect number of WORKING DAYS per week (%d)\n", MAX_AMOUNT_OF_WORKING_DAYS_PER_WEEK);
        } else {
            System.out.printf("\nSelect number of WORKING DAYS per week (%d-%d)\n", getMinAmountOfWorkingDaysPerWeek(groups), MAX_AMOUNT_OF_WORKING_DAYS_PER_WEEK);
        }
        Scanner scanner = new Scanner(System.in);
        if (!(scanner.hasNextInt())) {
            logger.error(String.format("Exception: The entered data \"%s\" does not match the condition", scanner.next()));
            System.out.println("Exception: You entered invalid values. Try again!");
            getAmountOfWorkingDaysPerAWeek(groups);
        } else {
            int answer = scanner.nextInt();
            try {
                ScannerUtils.checkCorrectValue(answer, getMinAmountOfWorkingDaysPerWeek(groups), MAX_AMOUNT_OF_WORKING_DAYS_PER_WEEK);
                amountOfWorkingDaysPerAWeek = answer;
                logger.info("Accepted number of working days is " + amountOfWorkingDaysPerAWeek);
            } catch (InputException e) {
                System.out.println("Problem occurred: Invalid characters were entered in the field");
                getAmountOfWorkingDaysPerAWeek(groups);
            }
        }
        return amountOfWorkingDaysPerAWeek;
    }

    public static int getMaxNumberOfLessonsPerDay(List<Group> groups) {
        logger.info("The number of MAXIMUM lessons per day is requested");
        if (getMinPossibleLessonsPerDay(groups) == MAX_NUM_OF_SLOTS_PER_DAY) {
            System.out.printf("\nSelect MAXIMUM number of lessons per day (%d)\n", MAX_NUM_OF_SLOTS_PER_DAY);
        } else {
            System.out.printf("\nSelect MAXIMUM number of lessons per day (%d-%d)\n", getMinPossibleLessonsPerDay(groups), MAX_NUM_OF_SLOTS_PER_DAY);
        }
        Scanner sc = new Scanner(System.in);
        if (!(sc.hasNextInt())) {
            logger.error(String.format("Exception: The entered data \"%s\" does not match the condition\"", sc.next()));
            System.out.println("Exception: You entered invalid values. Try again!");
            getMaxNumberOfLessonsPerDay(groups);
        } else {
            int answer = Integer.parseInt(sc.next());
            try {
                ScannerUtils.checkCorrectValue(answer, getMinPossibleLessonsPerDay(groups), MAX_NUM_OF_SLOTS_PER_DAY);
                maxNumberOfLessonsPerDay = answer;
                logger.info("Accepted number of maximum lessons is " + maxNumberOfLessonsPerDay);
            } catch (InputException e) {
                System.out.println("Problem occurred: Invalid characters were entered in the field.");
                getMaxNumberOfLessonsPerDay(groups);
            }
        }
        return maxNumberOfLessonsPerDay;
    }

    public static int getMinNumberOfLessonsPerDay(List<Group> groups) {
        logger.info("The number of MINIMUM lessons per day is requested");
        if (getMinPossibleLessonsPerDay(groups) == maxLessonsPerDay) {
            System.out.printf("\nSelect MINIMUM number of lessons per day (%d)\n", getMinPossibleLessonsPerDay(groups));
        } else {
            System.out.printf("\nSelect MINIMUM number of lessons per day (%d-%d)\n", getMinPossibleLessonsPerDay(groups), maxLessonsPerDay);
        }
        Scanner scan = new Scanner(System.in);
        if (!(scan.hasNextInt())) {
            logger.error(String.format("Exception: The entered data \"%s\" does not match the condition\"", scan.next()));
            System.out.println("Exception: You entered invalid values. Try again!");
            getMinNumberOfLessonsPerDay(groups);
        } else {
            int answer = scan.nextInt();
            try {
                ScannerUtils.checkCorrectValue(answer, getMinPossibleLessonsPerDay(groups), maxLessonsPerDay);

                minNumberOfLessonsPerDay = answer;
                logger.info("Accepted number of minimum lessons is " + minNumberOfLessonsPerDay);
            } catch (InputException e) {
                System.out.println("Problem occurred: Invalid characters were entered in the field.");
                getMinNumberOfLessonsPerDay(groups);
            }
        }
        return minNumberOfLessonsPerDay;
    }
}
