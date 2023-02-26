package com.solvd.scheduler.terminal;

import com.solvd.scheduler.algorithm.ChromosomeInput;
import com.solvd.scheduler.exception.InputException;

import java.io.IOException;

public class TerminalUtils {

    public static int getMinAmountOfWorkingDaysPerWeek(int hoursPerWeek) throws InputException {
        return Math.ceilDiv(hoursPerWeek, ChromosomeInput.SCHOOL_WORKING_HOURS);
    }

    public static int getMaxAmountOfWorkingDaysPerWeek(int hoursPerWeek) {
        if (hoursPerWeek <= 5) {
            return hoursPerWeek;
        } else {
            return ChromosomeInput.MAX_SCHOOL_WORKING_DAYS;
        }
    }

    public static int getMinPossibleLessonsPerDayForMinValue(int minHoursPerWeek) {
        return Math.max(minHoursPerWeek - ((TerminalInputInfo.daysPerWeek - 1) * ChromosomeInput.SCHOOL_WORKING_HOURS), TerminalInputInfo.DAY_MAY_CONTAIN_A_MIN_OF_LESSONS);
    }

    public static int getMaxPossibleLessonsPerDayForMinValue(int minHoursPerWeek) throws InputException {
        return Math.max(Math.floorDiv(minHoursPerWeek, TerminalInputInfo.daysPerWeek), TerminalInputInfo.minLessonsPerDay);
    }

    public static int getMinPossibleLessonsPerDayForMaxValue(int maxHoursPerWeek) {
        return Math.ceilDiv(maxHoursPerWeek, TerminalInputInfo.daysPerWeek);
    }

    public static int getMaxPossibleLessonsPerDayForMaxValue(int maxHoursPerWeek) {
        if (maxHoursPerWeek <= ChromosomeInput.SCHOOL_WORKING_HOURS & maxHoursPerWeek != 0) {
            return maxHoursPerWeek - ((TerminalInputInfo.daysPerWeek - 1) * TerminalInputInfo.minLessonsPerDay);
        } else {
            return Math.min(maxHoursPerWeek - ((TerminalInputInfo.daysPerWeek - 1) * TerminalInputInfo.minLessonsPerDay), ChromosomeInput.SCHOOL_WORKING_HOURS);
        }
    }

    public static void checkCorrectValue(int answer, int comparativeNumber1, int comparativeNumber2) throws InputException {
        boolean valid = answer >= comparativeNumber1 && answer <= comparativeNumber2;
        if (!(valid)) {
            throw new InputException(String.format("\nYou can only select integer numbers from %d to %d", comparativeNumber1, comparativeNumber2), new IOException(), answer);
        }
    }
}
