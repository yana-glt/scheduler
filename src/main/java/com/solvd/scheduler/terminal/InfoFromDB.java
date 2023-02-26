package com.solvd.scheduler.terminal;

import com.solvd.scheduler.model.Group;
import com.solvd.scheduler.model.Subject;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class InfoFromDB {

    public static void printDataAboutGroupsWithSubjects(List<Group> groupsAndTheirSubjectWithTimePerWeek) {
        System.out.println("Here you can see information about the number of study hours for each class per week:");
        for (Group g : groupsAndTheirSubjectWithTimePerWeek) {
            System.out.print(g.getName() + ": ");
            HashMap<Subject, Integer> subjects = g.getSubjectAmountPerWeek();
            int count = 0;
            for (Map.Entry<Subject, Integer> item : subjects.entrySet()) {
                System.out.print(String.format("%s - %s; ", item.getKey().getName(), item.getValue()));
                count += item.getValue();
            }
            System.out.println(String.format("total: %s", count));
        }
    }

    public static void printDataAboutGroupsName(List<Group> groups) {
        for (Group g : groups) {
            System.out.println(g.getName());
        }
    }

    public static int getHoursInGroupWithMaxHoursPerWeek(List<Group> groups) {
        return groups.stream().map(p -> p.getSubjectsAsList().size()).max(Integer::compareTo).orElse(0);
    }

    public static int getHoursInGroupWithMinHoursPerWeek(List<Group> groups) {
        return groups.stream().map(p -> p.getSubjectsAsList().size()).min(Integer::compareTo).orElse(0);
    }
}