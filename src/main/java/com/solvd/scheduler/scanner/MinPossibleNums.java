package com.solvd.scheduler.scanner;

import com.solvd.scheduler.exception.InputException;
import com.solvd.scheduler.model.Group;

import java.io.IOException;
import java.util.List;

import static com.solvd.scheduler.scanner.DbInfo.getAmountOfHoursPerWeek;
import static com.solvd.scheduler.scanner.TerminalInputInfo.*;

public class MinPossibleNums {
    public static int getMinAmountOfWorkingDaysPerWeek(List<Group> groups) throws InputException {
        int minAmountOfWorkingDaysPerWeek = Math.ceilDiv(getAmountOfHoursPerWeek(groups), MAX_NUM_OF_SLOTS_PER_DAY);
        if (minAmountOfWorkingDaysPerWeek < Math.ceilDiv(getAmountOfHoursPerWeek(groups), MAX_NUM_OF_SLOTS_PER_DAY)) {
            throw new InputException("\nYou need to schedule more days to study the subjects planned for the week", new IOException());
        } else {
            return minAmountOfWorkingDaysPerWeek;
        }
    }

    public static int getMinPossibleLessonsPerDay(List<Group> groups) {
        return Math.ceilDiv(getAmountOfHoursPerWeek(groups), daysPerWeek);
    }
}
