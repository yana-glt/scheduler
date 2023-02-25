package com.solvd.scheduler.scanner;

import com.solvd.scheduler.exception.InputException;

import static com.solvd.scheduler.scanner.TerminalInputInfo.*;

public class MinMaxPossibleNums {
    public static int getMinAmountOfWorkingDaysPerWeek() throws InputException {
        return Math.ceilDiv(hoursPerWeekByMax, MAX_NUM_OF_SLOTS_PER_DAY);
    }

    public static int getMaxAmountOfWorkingDaysPerWeek() {
        if (hoursPerWeekByMax <= 5) {
            return hoursPerWeekByMax;
        } else {
            return MAX_AMOUNT_OF_WORKING_DAYS_PER_WEEK;
        }
    }

    public static int getMinPossibleLessonsPerDayForMinValue() {
        return Math.max(hoursPerWeekByMin - ((daysPerWeek - 1) * MAX_NUM_OF_SLOTS_PER_DAY), DAY_MAY_CONTAIN_A_MIN_OF_LESSONS);
    }

    public static int getMaxPossibleLessonsPerDayForMinValue() throws InputException {
        return Math.max(Math.floorDiv(hoursPerWeekByMin, daysPerWeek), minLessonsPerDay);
    }

    public static int getMinPossibleLessonsPerDayForMaxValue() {
        return Math.ceilDiv(hoursPerWeekByMax, daysPerWeek);
    }

    public static int getMaxPossibleLessonsPerDayForMaxValue() {
        if (hoursPerWeekByMax <= MAX_NUM_OF_SLOTS_PER_DAY & hoursPerWeekByMax != 0) {
            return hoursPerWeekByMax;
        } else {
            return MAX_NUM_OF_SLOTS_PER_DAY;
        }
    }
}
