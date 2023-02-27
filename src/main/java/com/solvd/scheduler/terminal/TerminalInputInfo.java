package com.solvd.scheduler.terminal;

import java.util.List;
import java.util.Scanner;

import com.solvd.scheduler.exception.InputException;
import com.solvd.scheduler.model.Group;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import static com.solvd.scheduler.terminal.TerminalUtils.*;
import static com.solvd.scheduler.terminal.InfoFromDB.getHoursInGroupWithMaxHoursPerWeek;
import static com.solvd.scheduler.terminal.InfoFromDB.getHoursInGroupWithMinHoursPerWeek;
import static java.lang.System.in;

public class TerminalInputInfo {
    private final static Logger LOGGER = LogManager.getLogger(TerminalInputInfo.class);
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
        LOGGER.debug("The number of working days is requested");
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
                LOGGER.error(String.format("Exception: The entered data \"%s\" does not match the condition", scanner.next()));
                System.out.println("You entered invalid value. Try again!");
                getAmountOfWorkingDaysPerAWeek(getMinAmountOfWorkingDaysPerWeek(maxHoursPerWeek), getMaxAmountOfWorkingDaysPerWeek(maxHoursPerWeek), maxHoursPerWeek, minHoursPerWeek);
            } else {
                do {
                    int answer = scanner.nextInt();
                    try {
                        TerminalUtils.checkCorrectValue(answer, minDays, maxDays);
                        amountOfWorkingDaysPerAWeek = answer;
                        LOGGER.debug("Accepted number of working days is " + amountOfWorkingDaysPerAWeek);
                    } catch (InputException e) {
                        System.out.println("You entered invalid value. Try again!");
                        getAmountOfWorkingDaysPerAWeek(getMinAmountOfWorkingDaysPerWeek(maxHoursPerWeek), getMaxAmountOfWorkingDaysPerWeek(maxHoursPerWeek), maxHoursPerWeek, maxHoursPerWeek);
                    }
                } while (amountOfWorkingDaysPerAWeek == 0);
            }
        }
        return amountOfWorkingDaysPerAWeek;
    }

    public static int getMinNumberOfLessonsPerDay(int lowerLimit, int upperLimit, int daysPerWeek, int minHoursPerWeek) throws InputException {
        LOGGER.debug("The number of MINIMUM lessons per day is requested");
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
                    LOGGER.error(String.format("Exception: The entered data \"%s\" does not match the condition\"", scan.next()));
                    System.out.println("You entered invalid value. Try again!");
                    getMinNumberOfLessonsPerDay(getMinPossibleLessonsPerDayForMinValue(minHoursPerWeek), getMaxPossibleLessonsPerDayForMinValue(minHoursPerWeek), daysPerWeek, minHoursPerWeek);
                } else {
                    do {
                        int answer = scan.nextInt();
                        try {
                            TerminalUtils.checkCorrectValue(answer, lowerLimit, upperLimit);
                            minNumberOfLessonsPerDay = answer;
                            LOGGER.debug("Accepted number of minimum lessons is " + minNumberOfLessonsPerDay);
                        } catch (InputException e) {
                            System.out.println("You entered invalid value. Try again!");
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
        LOGGER.debug("The number of MAXIMUM lessons per day is requested");
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
                    LOGGER.error(String.format("Exception: The entered data \"%s\" does not match the condition\"", sc.next()));
                    System.out.println("You entered invalid value. Try again!");
                    getMaxNumberOfLessonsPerDay(getMinPossibleLessonsPerDayForMaxValue(maxHoursPerWeek), getMaxPossibleLessonsPerDayForMaxValue(maxHoursPerWeek), maxHoursPerWeek);
                } else {
                    int answer = Integer.parseInt(sc.next());
                    try {
                        TerminalUtils.checkCorrectValue(answer, lowerLimit, upperLimit);
                        maxNumberOfLessonsPerDay = answer;
                        LOGGER.debug("Accepted number of maximum lessons is " + maxNumberOfLessonsPerDay);
                    } catch (InputException e) {
                        System.out.println("You entered invalid value. Try again!");
                        getMaxNumberOfLessonsPerDay(getMinPossibleLessonsPerDayForMaxValue(maxHoursPerWeek), getMaxPossibleLessonsPerDayForMaxValue(maxHoursPerWeek), maxHoursPerWeek);
                    }
                }
            }
            while (maxNumberOfLessonsPerDay == 0);
        }
        return maxNumberOfLessonsPerDay;
    }
}
