package com.solvd.scheduler;

import com.solvd.scheduler.algorithm.IScheduleGenerate;
import com.solvd.scheduler.algorithm.ScheduleGenerator;
import com.solvd.scheduler.model.Group;
import com.solvd.scheduler.service.implementation.GroupService;
import com.solvd.scheduler.service.interfaces.IGroupService;
import com.solvd.scheduler.terminal.InfoFromDB;
import com.solvd.scheduler.terminal.TerminalOutputInfo;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;
import java.util.stream.Collectors;

public class Main {
    private final static Logger logger = LogManager.getLogger(Main.class);
    private static final int NUM_DAYS = 5;
    private static final int MIN_LESSONS = 2;
    private static final int MAX_LESSONS = 8;

    public static void main(String[] args){
        logger.info("Check logger");
        IGroupService groupService = new GroupService();
        List<Group> groupsAndTheirSubjectWithTimePerWeek = groupService.groupsAndTheirSubjectWithTimePerWeek();
        InfoFromDB.printData(groupsAndTheirSubjectWithTimePerWeek);

//        for (Group g : groupsAndTheirSubjectWithTimePerWeek) {
//            System.out.println(g.toString());
//            System.out.println(g.getSubjectAmountPerWeek().keySet().stream().collect(Collectors.toList()));
//        }

        System.out.println();
        IScheduleGenerate sg = new ScheduleGenerator(groupsAndTheirSubjectWithTimePerWeek, NUM_DAYS, MIN_LESSONS, MAX_LESSONS);
        //ScheduleGenerator.print(sg.compute());
        TerminalOutputInfo.presentOutput(ScheduleGenerator.get(sg.compute()));
    }

}