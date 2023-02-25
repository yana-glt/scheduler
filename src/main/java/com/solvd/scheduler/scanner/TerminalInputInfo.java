package com.solvd.scheduler.scanner;

import java.util.List;
import java.util.Scanner;

import com.solvd.scheduler.exception.InputException;
import com.solvd.scheduler.model.Group;

import static com.solvd.scheduler.Main.logger;
import static com.solvd.scheduler.scanner.DbInfo.getHoursInGroupWithMaxHoursPerWeek;
import static com.solvd.scheduler.scanner.DbInfo.getHoursInGroupWithMinHoursPerWeek;
import static com.solvd.scheduler.scanner.MinMaxPossibleNums.*;

public class TerminalInputInfo {
    public static final int MAX_NUM_OF_SLOTS_PER_DAY = 8;
    public static final int MAX_AMOUNT_OF_WORKING_DAYS_PER_WEEK = 5;
    public static final int DAY_MAY_CONTAIN_A_MIN_OF_LESSONS = 1;
    public static int daysPerWeek;
    public static int maxLessonsPerDay;
    public static int minLessonsPerDay;
    public static int hoursPerWeekByMax;
    public static int hoursPerWeekByMin;
    private static int amountOfWorkingDaysPerAWeek;
    private static int minNumberOfLessonsPerDay;
    private static int maxNumberOfLessonsPerDay;

    public static void openScanner(List<Group> groups) throws InputException {
        hoursPerWeekByMax = getHoursInGroupWithMaxHoursPerWeek(groups);
        hoursPerWeekByMin = getHoursInGroupWithMinHoursPerWeek(groups);
        daysPerWeek = getAmountOfWorkingDaysPerAWeek();
        minLessonsPerDay = getMinNumberOfLessonsPerDay();
        maxLessonsPerDay = getMaxNumberOfLessonsPerDay();
    }

    public static int getAmountOfWorkingDaysPerAWeek() throws InputException {
        logger.info("The number of working days is requested");
        if (getMinAmountOfWorkingDaysPerWeek() == getMaxAmountOfWorkingDaysPerWeek() && hoursPerWeekByMax != 0 && hoursPerWeekByMin != 0) {
            System.out.printf("At least one group has amount of lessons per week that requires %d days.\n" +
                    "Number of working days is set to %d.\n", getMinAmountOfWorkingDaysPerWeek(), getMaxAmountOfWorkingDaysPerWeek());
            amountOfWorkingDaysPerAWeek = getMinAmountOfWorkingDaysPerWeek();
        } else if (hoursPerWeekByMin == 0 || hoursPerWeekByMax == 0) {
            System.out.println("No information about the number of lessons was found in the database.\nOr the total number of hours in groups is zero.\nFor this reason schedule can't be generated.\nPlease insert information about amount of hours for Subjects to DB.");
            System.exit(-1);
        } else {
            System.out.printf("\nSelect number of WORKING DAYS per week (%d-%d)\n", getMinAmountOfWorkingDaysPerWeek(), getMaxAmountOfWorkingDaysPerWeek());
            Scanner scanner = new Scanner(System.in);
            if (!(scanner.hasNextInt())) {
                logger.error(String.format("Exception: The entered data \"%s\" does not match the condition", scanner.next()));
                System.out.println("Exception: You entered invalid values. Try again!");
                getAmountOfWorkingDaysPerAWeek();
            } else {
                int answer = scanner.nextInt();
                try {
                    ScannerUtils.checkCorrectValue(answer, getMinAmountOfWorkingDaysPerWeek(), getMaxAmountOfWorkingDaysPerWeek());
                    amountOfWorkingDaysPerAWeek = answer;
                    logger.info("Accepted number of working days is " + amountOfWorkingDaysPerAWeek);
                } catch (InputException e) {
                    System.out.println("Problem occurred: Invalid characters were entered in the field");
                    getAmountOfWorkingDaysPerAWeek();
                }
            }
        }
        return amountOfWorkingDaysPerAWeek;
    }

    public static int getMinNumberOfLessonsPerDay() throws InputException {
        logger.info("The number of MINIMUM lessons per day is requested");
        if (hoursPerWeekByMin <= 5) {
            System.out.printf("\nAt least one group has amount of lessons per %d working day[s] that requires no more then 1 lesson per day.\n" +
                    "Minimum number of lessons is set to 1 by default.\n", daysPerWeek);
            minNumberOfLessonsPerDay = 1;
        } else {
            if (getMinPossibleLessonsPerDayForMinValue() == getMaxPossibleLessonsPerDayForMinValue()) {
                System.out.printf("\nA minimum of %d lessons per day is calculated and accepted - the only value suitable for all groups.\n", getMinPossibleLessonsPerDayForMinValue());
                minNumberOfLessonsPerDay = getMinPossibleLessonsPerDayForMinValue();
            } else {
                System.out.printf("\nSelect MINIMUM number of lessons per day (%d-%d)\n", getMinPossibleLessonsPerDayForMinValue(), getMaxPossibleLessonsPerDayForMinValue());
                Scanner scan = new Scanner(System.in);
                if (!(scan.hasNextInt())) {
                    logger.error(String.format("Exception: The entered data \"%s\" does not match the condition\"", scan.next()));
                    System.out.println("Exception: You entered invalid values. Try again!");
                    getMinNumberOfLessonsPerDay();
                } else {
                    int answer = scan.nextInt();
                    try {
                        ScannerUtils.checkCorrectValue(answer, getMinPossibleLessonsPerDayForMinValue(), getMaxPossibleLessonsPerDayForMinValue());
                        minNumberOfLessonsPerDay = answer;
                        logger.info("Accepted number of minimum lessons is " + minNumberOfLessonsPerDay);
                    } catch (InputException e) {
                        System.out.println("Problem occurred: Invalid characters were entered in the field.");
                        getMinNumberOfLessonsPerDay();
                    }
                }
            }
        }
        return minNumberOfLessonsPerDay;
    }


    public static int getMaxNumberOfLessonsPerDay() {
        logger.info("The number of MAXIMUM lessons per day is requested");
        if (getMinPossibleLessonsPerDayForMaxValue() == getMaxPossibleLessonsPerDayForMaxValue()) {
            System.out.printf("Maximum number of lessons per day required to allocate all lessons is %d.\nSo max number" +
                    " of lessons is set to %d by default\n", getMaxPossibleLessonsPerDayForMaxValue(), getMaxPossibleLessonsPerDayForMaxValue());
            minNumberOfLessonsPerDay = getMaxPossibleLessonsPerDayForMaxValue();
        } else {
            System.out.printf("\nSelect MAXIMUM number of lessons per day (%d-%d)\n", getMinPossibleLessonsPerDayForMaxValue(), getMaxPossibleLessonsPerDayForMaxValue());
            Scanner sc = new Scanner(System.in);
            if (!(sc.hasNextInt())) {
                logger.error(String.format("Exception: The entered data \"%s\" does not match the condition\"", sc.next()));
                System.out.println("Exception: You entered invalid values. Try again!");
                getMaxNumberOfLessonsPerDay();
            } else {
                int answer = Integer.parseInt(sc.next());
                try {
                    ScannerUtils.checkCorrectValue(answer, getMinPossibleLessonsPerDayForMaxValue(), getMaxPossibleLessonsPerDayForMaxValue());
                    maxNumberOfLessonsPerDay = answer;
                    logger.info("Accepted number of maximum lessons is " + maxNumberOfLessonsPerDay);
                } catch (InputException e) {
                    System.out.println("Problem occurred: Invalid characters were entered in the field.");
                    getMaxNumberOfLessonsPerDay();
                }
            }
        }
        return maxNumberOfLessonsPerDay;
    }
}
