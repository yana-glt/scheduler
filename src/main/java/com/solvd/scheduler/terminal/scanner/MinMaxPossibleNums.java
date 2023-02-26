package com.solvd.scheduler.terminal.scanner;

import com.solvd.scheduler.exception.InputException;

public class MinMaxPossibleNums {
    public static int getMinAmountOfWorkingDaysPerWeek(int hoursPerWeek) throws InputException {
        return Math.ceilDiv(hoursPerWeek, TerminalInputInfo.MAX_NUM_OF_SLOTS_PER_DAY);
    }

    public static int getMaxAmountOfWorkingDaysPerWeek(int hoursPerWeek) {
        if (hoursPerWeek <= 5) {
            return hoursPerWeek;
        } else {
            return TerminalInputInfo.MAX_AMOUNT_OF_WORKING_DAYS_PER_WEEK;
        }
    }

    public static int getMinPossibleLessonsPerDayForMinValue(int minHoursPerWeek) {
        return Math.max(minHoursPerWeek - ((TerminalInputInfo.daysPerWeek - 1) * TerminalInputInfo.MAX_NUM_OF_SLOTS_PER_DAY), TerminalInputInfo.DAY_MAY_CONTAIN_A_MIN_OF_LESSONS);
    }

    public static int getMaxPossibleLessonsPerDayForMinValue(int minHoursPerWeek) throws InputException {
        return Math.max(Math.floorDiv(minHoursPerWeek, TerminalInputInfo.daysPerWeek), TerminalInputInfo.minLessonsPerDay);
    }

    public static int getMinPossibleLessonsPerDayForMaxValue(int maxHoursPerWeek) {
        return Math.ceilDiv(maxHoursPerWeek, TerminalInputInfo.daysPerWeek);
    }

    public static int getMaxPossibleLessonsPerDayForMaxValue(int maxHoursPerWeek) {
        if (maxHoursPerWeek <= TerminalInputInfo.MAX_NUM_OF_SLOTS_PER_DAY & maxHoursPerWeek != 0) {
            return maxHoursPerWeek - ((TerminalInputInfo.daysPerWeek - 1) * TerminalInputInfo.minLessonsPerDay);
        } else {
            return Math.min(maxHoursPerWeek - ((TerminalInputInfo.daysPerWeek - 1) * TerminalInputInfo.minLessonsPerDay), TerminalInputInfo.MAX_NUM_OF_SLOTS_PER_DAY);
        }
    }
}
