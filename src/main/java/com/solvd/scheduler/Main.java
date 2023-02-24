package com.solvd.scheduler;

import com.solvd.scheduler.model.Group;
import com.solvd.scheduler.model.Subject;
import com.solvd.scheduler.service.implementation.GroupService;
import com.solvd.scheduler.service.interfaces.IGroupService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class Main {
    private final static Logger logger = LogManager.getLogger(Main.class);
    private static final int NUM_DAYS = 5;
    private static final int MIN_LESSONS = 2;
    private static final int MAX_LESSONS = 8;

    public static void main(String[] args) {
        List<Group> groups=  GroupService.gettingGroupWithSubjectImplementation();
        for (Group g : groups) {
            System.out.println(g.toString());
            ArrayList<Subject> subjects=new ArrayList<>(g.getSubjectAmountPerWeek().keySet());
            System.out.println(subjects);
        }
    }
}