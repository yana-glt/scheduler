package com.solvd.scheduler;

import com.solvd.scheduler.algorithm.IScheduleGenerate;
import com.solvd.scheduler.algorithm.ScheduleGenerator;
import com.solvd.scheduler.model.Group;
import com.solvd.scheduler.model.Lesson;
import com.solvd.scheduler.service.implementation.GroupService;
//import com.solvd.scheduler.terminal.TerminalOutputInfo;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import java.util.List;

public class Main {
    private final static Logger logger = LogManager.getLogger(Main.class);
    private static final int NUM_DAYS = 5;
    private static final int MIN_LESSONS = 2;
    private static final int MAX_LESSONS = 8;

    public static void main(String[] args) {
        List<Group> groups = GroupService.gettingGroupWithSubjectImplementation();
        //int[] ints = TerminalInputInfo.openScanner(groups);
        //IScheduleGenerate sg = new ScheduleGenerator(groups, ints[0], ints[1], ints[2]);
        //List<Lesson> lessons = sg.compute();
        //TerminalOutputInfo.presentOutput(lessons);
    }
}