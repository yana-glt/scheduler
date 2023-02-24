package com.solvd.scheduler.scanner;

import com.solvd.scheduler.exception.InputException;

import static com.solvd.scheduler.scanner.TerminalInputInfo.*;

public class MinMaxPossibleNums {
    public static int getMinAmountOfWorkingDaysPerWeek() throws InputException {
        if (Math.ceilDiv(hoursPerWeekByMax, MAX_NUM_OF_SLOTS_PER_DAY) > MAX_AMOUNT_OF_WORKING_DAYS_PER_WEEK) {
            return MAX_AMOUNT_OF_WORKING_DAYS_PER_WEEK;
        } else {
            return Math.ceilDiv(hoursPerWeekByMax, MAX_NUM_OF_SLOTS_PER_DAY);
        }
    }

    public static int getMinPossibleLessonsPerDayForMaxValue() {
        return hoursPerWeekByMax / daysPerWeek;
//        return Math.max(Math.ceilDiv((hoursPerWeekByMax - minLessonsPerDay), (daysPerWeek - 1)), minLessonsPerDay);
//        return Math.ceilDiv(hoursPerWeekByMax, daysPerWeek);
    }

    public static int getMinPossibleLessonsPerDayForMinValue() {
        return Math.max(hoursPerWeekByMin - ((daysPerWeek - 1) * MAX_NUM_OF_SLOTS_PER_DAY), DAY_MAY_CONTAIN_A_MIN_OF_LESSONS);
//        return Math.max(hoursPerWeekByMin - ((daysPerWeek - 1) * maxLessonsPerDay), DAY_MAY_CONTAIN_A_MIN_OF_LESSONS);
    }

    public static int getMaxPossibleLessonsPerDayForMinValue() throws InputException {
        int limitByMin = hoursPerWeekByMin / daysPerWeek;
        return Math.max(Math.min(Math.floorDiv((hoursPerWeekByMax - MAX_NUM_OF_SLOTS_PER_DAY), (daysPerWeek - 1)), maxLessonsPerDay), limitByMin);
//        int limitByMin =  hoursPerWeekByMin / daysPerWeek;
//        return Math.max(Math.min(Math.floorDiv((hoursPerWeekByMin - maxLessonsPerDay), (daysPerWeek - 1)), maxLessonsPerDay), limitByMin);
    }
}

