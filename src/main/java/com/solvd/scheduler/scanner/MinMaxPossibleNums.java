package com.solvd.scheduler.scanner;

import com.solvd.scheduler.exception.InputException;

import static com.solvd.scheduler.scanner.TerminalInputInfo.*;

public class MinMaxPossibleNums {
    public static int getMinAmountOfWorkingDaysPerWeek() throws InputException {
        return Math.ceilDiv(hoursPerWeekByMax, MAX_NUM_OF_SLOTS_PER_DAY);
    }

    public static int getMinPossibleLessonsPerDayForMaxValue() {
        return Math.ceilDiv(hoursPerWeekByMin, daysPerWeek);
    }

    public static int getMinPossibleLessonsPerDayForMinValue() {
        return Math.max(hoursPerWeekByMin - ((daysPerWeek - 1) * maxLessonsPerDay), DAY_MAY_CONTAIN_A_MIN_OF_LESSONS);
    }

    public static int getMaxPossibleLessonsPerDayForMinValue() throws InputException {
        return Math.min(Math.floorDiv((hoursPerWeekByMax - maxLessonsPerDay), (daysPerWeek - 1)), maxLessonsPerDay);
    }
}
