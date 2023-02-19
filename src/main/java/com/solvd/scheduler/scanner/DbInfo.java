package com.solvd.scheduler.scanner;

import com.solvd.scheduler.model.Group;

import java.util.List;

public class DbInfo {
    public static int getAmountOfHoursPerWeek(List<Group> groups) {
        int amountOfHoursPerWeek = 0;
        for (Group g : groups) {
            int sumOfHoursInGroup = g.getSubjectAmountPerWeek().values().stream().mapToInt(Integer::intValue).sum();
            if (sumOfHoursInGroup > amountOfHoursPerWeek) {
                amountOfHoursPerWeek = sumOfHoursInGroup;
            }
        }
        return amountOfHoursPerWeek;
    }
}
