package com.solvd.scheduler.util;

import com.solvd.scheduler.model.Group;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Schedule{

    public static int minDaysToSatisfySchedule(List<Group> groups){
        int minHour;
        int sum = 0;
        int minDay;
        final int HOURS_PER_DAY = 8;
        final int WORKING_DAYS_PER_WEEK = 6;
        Set<Integer> subjects = new HashSet<>();
        for (Group g : groups){
            sum += g.getSubjectAmountPerWeek().values().stream().mapToInt(Integer::intValue).sum();
            g.getSubjectAmountPerWeek().keySet().stream().forEach(subject -> subjects.add((int) subject.getId()));

            // get the names of all subjects and put it in the subject HashSet and then I will have the amount of subjects
            // and then find a formula
            // that will help we tell that this classes are happening but not simultaneously
        }
        System.out.println("---All Subjects---");
        subjects.stream().forEach(System.out::println);
        System.out.println("sum : " + sum);
        minHour = sum / WORKING_DAYS_PER_WEEK;
        System.out.println("minimum hour: " + minHour);
        minDay = minHour / HOURS_PER_DAY;
        return minDay;
    }

}
