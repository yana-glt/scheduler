package com.solvd.scheduler.terminal;

import com.solvd.scheduler.exception.InputException;

import java.io.IOException;

import static com.solvd.scheduler.algorithm.ChromosomeInput.MAX_SCHOOL_WORKING_DAYS;
import static com.solvd.scheduler.algorithm.ChromosomeInput.SCHOOL_WORKING_HOURS;
import static com.solvd.scheduler.terminal.TerminalInputInfo.*;

public class TerminalUtils {

    public static int getMinAmountOfWorkingDaysPerWeek(int hoursPerWeek) throws InputException {
        return Math.ceilDiv(hoursPerWeek, SCHOOL_WORKING_HOURS);
    }

    public static int getMaxAmountOfWorkingDaysPerWeek(int hoursPerWeek) {
        if (hoursPerWeek <= 5) {
            return hoursPerWeek;
        } else {
            return MAX_SCHOOL_WORKING_DAYS;
        }
    }

    public static int getMinPossibleLessonsPerDayForMinValue(int minHoursPerWeek) {
        return Math.max(minHoursPerWeek - ((daysPerWeek - 1) * SCHOOL_WORKING_HOURS), DAY_MAY_CONTAIN_A_MIN_OF_LESSONS);
    }

    public static int getMaxPossibleLessonsPerDayForMinValue(int minHoursPerWeek) throws InputException {
        return Math.max(Math.floorDiv(minHoursPerWeek, daysPerWeek), minLessonsPerDay);
    }

    public static int getMinPossibleLessonsPerDayForMaxValue(int maxHoursPerWeek) {
        return Math.ceilDiv(maxHoursPerWeek, daysPerWeek);
    }

    public static int getMaxPossibleLessonsPerDayForMaxValue(int maxHoursPerWeek) {
            return Math.min(maxHoursPerWeek - ((daysPerWeek - 1) * minLessonsPerDay), SCHOOL_WORKING_HOURS);
    }

    public static void checkCorrectValue(int answer, int comparativeNumber1, int comparativeNumber2) throws InputException {
        boolean valid = answer >= comparativeNumber1 && answer <= comparativeNumber2;
        if (!(valid)) {
            throw new InputException(String.format("\nYou can only select integer numbers from %d to %d", comparativeNumber1, comparativeNumber2), new IOException(), answer);
        }
    }
}
