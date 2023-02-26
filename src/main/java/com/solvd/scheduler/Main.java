package com.solvd.scheduler;

import com.solvd.scheduler.algorithm.IScheduleGenerate;
import com.solvd.scheduler.algorithm.Individual;
import com.solvd.scheduler.algorithm.ScheduleGenerator;
import com.solvd.scheduler.exception.InputException;
import com.solvd.scheduler.model.Group;
import com.solvd.scheduler.scanner.TerminalInputInfo;
import com.solvd.scheduler.service.implementation.GroupService;
import com.solvd.scheduler.service.interfaces.IGroupService;
import com.solvd.scheduler.terminal.InfoFromDB;
import com.solvd.scheduler.terminal.TerminalOutputInfo.*;
import com.solvd.scheduler.model.Lesson;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import java.util.List;

public class Main {
    private final static Logger logger = LogManager.getLogger(Main.class);

    public static void main(String[] args) {
        List<Group> groups = GroupService.gettingGroupWithSubjectImplementation();
        TerminalInputInfo.openScanner(groupsAndTheirSubjectWithTimePerWeek);
        IScheduleGenerate sg = new ScheduleGenerator(groupsAndTheirSubjectWithTimePerWeek, daysPerWeek, minLessonsPerDay, maxLessonsPerDay);
        TerminalOutputInfo.presentOutput(new Individual(sg.compute()));
    }
}