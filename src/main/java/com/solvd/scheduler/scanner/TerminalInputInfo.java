package com.solvd.scheduler.scanner;

import java.util.List;
import java.util.Scanner;

import com.solvd.scheduler.exception.InputException;
import com.solvd.scheduler.model.Group;

import static com.solvd.scheduler.Main.logger;
import static com.solvd.scheduler.scanner.DbInfo.getHoursInGroupWithMaxHoursPerWeek;
import static com.solvd.scheduler.scanner.DbInfo.getHoursInGroupWithMinHoursPerWeek;
import static com.solvd.scheduler.scanner.MinMaxPossibleNums.*;
import static java.lang.System.in;

public class TerminalInputInfo {
    public static final int MAX_NUM_OF_SLOTS_PER_DAY = 8;
    public static final int MAX_AMOUNT_OF_WORKING_DAYS_PER_WEEK = 5;
    public static final int DAY_MAY_CONTAIN_A_MIN_OF_LESSONS = 1;
    public static int daysPerWeek;
    public static int maxLessonsPerDay;
    public static int minLessonsPerDay;
    private static int amountOfWorkingDaysPerAWeek;
    private static int minNumberOfLessonsPerDay;
    private static int maxNumberOfLessonsPerDay;

    public static void openScanner(List<Group> groups) throws InputException {
        int hoursPerWeekByMax = getHoursInGroupWithMaxHoursPerWeek(groups);
        int hoursPerWeekByMin = getHoursInGroupWithMinHoursPerWeek(groups);
        daysPerWeek = getAmountOfWorkingDaysPerAWeek(getMinAmountOfWorkingDaysPerWeek(hoursPerWeekByMax), getMaxAmountOfWorkingDaysPerWeek(hoursPerWeekByMax), hoursPerWeekByMax, hoursPerWeekByMin);
        minLessonsPerDay = getMinNumberOfLessonsPerDay(getMinPossibleLessonsPerDayForMinValue(hoursPerWeekByMin), getMaxPossibleLessonsPerDayForMinValue(hoursPerWeekByMin), daysPerWeek, hoursPerWeekByMin);
        maxLessonsPerDay = getMaxNumberOfLessonsPerDay(getMinPossibleLessonsPerDayForMaxValue(hoursPerWeekByMax), getMaxPossibleLessonsPerDayForMaxValue(hoursPerWeekByMax), hoursPerWeekByMax);
    }

    public static int getAmountOfWorkingDaysPerAWeek(int minDays, int maxDays, int maxHoursPerWeek, int minHoursPerWeek) throws InputException {
        logger.debug("The number of working days is requested");
        if (minDays == maxDays && maxHoursPerWeek != 0 && minHoursPerWeek != 0) {
            System.out.printf("""
                                        
                    At least one group has amount of lessons per week that requires %d days.
                    Number of working days is set to %d.
                    """, minDays, maxDays);
            amountOfWorkingDaysPerAWeek = maxDays;
        } else if (minHoursPerWeek == 0 || maxHoursPerWeek == 0) {
            System.out.println("""
                    No information about the number of lessons was found in the database.
                    Or the total number of hours in groups is zero.
                    For this reason schedule can't be generated.
                    Please insert information about amount of hours for Subjects to DB.""");
            System.exit(-1);
        } else {
            System.out.printf("""

                    Select number of WORKING DAYS per week (%d-%d)
                    """, minDays, maxDays);
            Scanner scanner = new Scanner(System.in);
            if (!(scanner.hasNextInt())) {
                logger.error(String.format("Exception: The entered data \"%s\" does not match the condition", scanner.next()));
                System.out.println("Exception: You entered invalid values. Try again!");
                getAmountOfWorkingDaysPerAWeek(getMinAmountOfWorkingDaysPerWeek(maxHoursPerWeek), getMaxAmountOfWorkingDaysPerWeek(maxHoursPerWeek), maxHoursPerWeek, minHoursPerWeek);
            } else {
                do {
                    int answer = scanner.nextInt();
                    try {
                        ScannerUtils.checkCorrectValue(answer, minDays, maxDays);
                        amountOfWorkingDaysPerAWeek = answer;
                        logger.debug("Accepted number of working days is " + amountOfWorkingDaysPerAWeek);
                    } catch (InputException e) {
                        System.out.println("Problem occurred: Invalid characters were entered in the field");
                        getAmountOfWorkingDaysPerAWeek(getMinAmountOfWorkingDaysPerWeek(maxHoursPerWeek), getMaxAmountOfWorkingDaysPerWeek(maxHoursPerWeek), maxHoursPerWeek, maxHoursPerWeek);
                    }
                } while (amountOfWorkingDaysPerAWeek == 0);
            }
        }
        return amountOfWorkingDaysPerAWeek;
    }

    public static int getMinNumberOfLessonsPerDay(int lowerLimit, int upperLimit, int daysPerWeek, int minHoursPerWeek) throws InputException {
        logger.debug("The number of MINIMUM lessons per day is requested");
        if (minHoursPerWeek <= 5 && Math.ceilDiv(minHoursPerWeek, daysPerWeek) == 1) {
            System.out.printf("""

                    At least one group has amount of lessons per %d working day[s] that requires no more then 1 lesson per day.
                    Minimum number of lessons is set to 1 by default.
                    """, daysPerWeek);
            minNumberOfLessonsPerDay = 1;
        } else {
            if (lowerLimit == upperLimit) {
                System.out.printf("""

                        A minimum of %d lessons per day is calculated and accepted - the only value suitable for all groups.
                        """, lowerLimit);
                minNumberOfLessonsPerDay = lowerLimit;
            } else {
                System.out.printf("""

                        Select MINIMUM number of lessons per day (%d-%d)
                        """, lowerLimit, upperLimit);
                Scanner scan = new Scanner(in);
                if (!(scan.hasNextInt())) {
                    logger.error(String.format("Exception: The entered data \"%s\" does not match the condition\"", scan.next()));
                    System.out.println("Exception: You entered invalid values. Try again!");
                    getMinNumberOfLessonsPerDay(getMinPossibleLessonsPerDayForMinValue(minHoursPerWeek), getMaxPossibleLessonsPerDayForMinValue(minHoursPerWeek), daysPerWeek, minHoursPerWeek);
                } else {
                    do {
                        int answer = scan.nextInt();
                        try {
                            ScannerUtils.checkCorrectValue(answer, lowerLimit, upperLimit);
                            minNumberOfLessonsPerDay = answer;
                            logger.debug("Accepted number of minimum lessons is " + minNumberOfLessonsPerDay);
                        } catch (InputException e) {
                            System.out.println("Problem occurred: Invalid characters were entered in the field.");
                            getMinNumberOfLessonsPerDay(getMinPossibleLessonsPerDayForMinValue(minHoursPerWeek), getMaxPossibleLessonsPerDayForMinValue(minHoursPerWeek), daysPerWeek, minHoursPerWeek);
                        }
                    }
                    while (minNumberOfLessonsPerDay == 0);
                }
            }
        }
        return minNumberOfLessonsPerDay;
    }

    public static int getMaxNumberOfLessonsPerDay(int lowerLimit, int upperLimit, int maxHoursPerWeek) {
        logger.debug("The number of MAXIMUM lessons per day is requested");
        if (lowerLimit == upperLimit) {
            System.out.printf("""
                    Maximum number of lessons per day required to allocate all lessons is %d.
                    So max number of lessons is set to %d by default
                    """, upperLimit, upperLimit);
            minNumberOfLessonsPerDay = upperLimit;
        } else {
            do {
                System.out.printf("""

                        Select MAXIMUM number of lessons per day (%d-%d)
                        """, lowerLimit, upperLimit);
                Scanner sc = new Scanner(in);
                if (!(sc.hasNextInt())) {
                    logger.error(String.format("Exception: The entered data \"%s\" does not match the condition\"", sc.next()));
                    System.out.println("Exception: You entered invalid values. Try again!");
                    getMaxNumberOfLessonsPerDay(getMinPossibleLessonsPerDayForMaxValue(maxHoursPerWeek), getMaxPossibleLessonsPerDayForMaxValue(maxHoursPerWeek), maxHoursPerWeek);
                } else {
                    int answer = Integer.parseInt(sc.next());
                    try {
                        ScannerUtils.checkCorrectValue(answer, lowerLimit, upperLimit);
                        maxNumberOfLessonsPerDay = answer;
                        logger.debug("Accepted number of maximum lessons is " + maxNumberOfLessonsPerDay);
                    } catch (InputException e) {
                        System.out.println("Problem occurred: Invalid characters were entered in the field.");
                        getMaxNumberOfLessonsPerDay(getMinPossibleLessonsPerDayForMaxValue(maxHoursPerWeek), getMaxPossibleLessonsPerDayForMaxValue(maxHoursPerWeek), maxHoursPerWeek);
                    }
                }
            }
            while (maxNumberOfLessonsPerDay == 0);
        }
        return maxNumberOfLessonsPerDay;
    }
}
