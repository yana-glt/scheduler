package com.solvd.scheduler.scanner;

import com.solvd.scheduler.exception.InputException;

import static com.solvd.scheduler.scanner.TerminalInputInfo.*;

public class MinMaxPossibleNums {
    public static int getMinAmountOfWorkingDaysPerWeek(int hoursPerWeek) throws InputException {
        return Math.ceilDiv(hoursPerWeek, MAX_NUM_OF_SLOTS_PER_DAY);
    }

    public static int getMaxAmountOfWorkingDaysPerWeek(int hoursPerWeek) {
        if (hoursPerWeek <= 5) {
            return hoursPerWeek;
        } else {
            return MAX_AMOUNT_OF_WORKING_DAYS_PER_WEEK;
        }
    }

    public static int getMinPossibleLessonsPerDayForMinValue(int minHoursPerWeek) {
        return Math.max(minHoursPerWeek - ((daysPerWeek - 1) * MAX_NUM_OF_SLOTS_PER_DAY), DAY_MAY_CONTAIN_A_MIN_OF_LESSONS);
    }

    public static int getMaxPossibleLessonsPerDayForMinValue(int minHoursPerWeek) throws InputException {
        return Math.max(Math.floorDiv(minHoursPerWeek, daysPerWeek), minLessonsPerDay);
    }

    public static int getMinPossibleLessonsPerDayForMaxValue(int maxHoursPerWeek) {
        return Math.ceilDiv(maxHoursPerWeek, daysPerWeek);
    }

    public static int getMaxPossibleLessonsPerDayForMaxValue(int maxHoursPerWeek) {
        if (maxHoursPerWeek <= MAX_NUM_OF_SLOTS_PER_DAY & maxHoursPerWeek != 0) {
            return maxHoursPerWeek - ((daysPerWeek - 1) * minLessonsPerDay);
        } else {
            return Math.min(maxHoursPerWeek - ((daysPerWeek - 1) * minLessonsPerDay), MAX_NUM_OF_SLOTS_PER_DAY);
        }
    }
}
