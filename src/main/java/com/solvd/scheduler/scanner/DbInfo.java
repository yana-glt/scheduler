package com.solvd.scheduler.scanner;

import com.solvd.scheduler.model.Group;

import java.util.List;

public class DbInfo {

    public static int getHoursInGroupWithMaxHoursPerWeek(List<Group> groups) {
        return groups.stream().map(p -> p.getSubjectsAsList().size()).max(Integer::compareTo).orElse(0);
    }

    public static int getHoursInGroupWithMinHoursPerWeek(List<Group> groups) {
        return groups.stream().map(p -> p.getSubjectsAsList().size()).min(Integer::compareTo).orElse(0);
    }
}
