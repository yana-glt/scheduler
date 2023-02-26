package com.solvd.scheduler;

import com.solvd.scheduler.algorithm.IScheduleGenerate;
import com.solvd.scheduler.algorithm.ScheduleGenerator;
import com.solvd.scheduler.exception.InputException;
import com.solvd.scheduler.model.Group;
import com.solvd.scheduler.terminal.scanner.TerminalInputInfo;
import com.solvd.scheduler.service.implementation.GroupService;
import com.solvd.scheduler.terminal.TerminalOutputInfo;
import com.solvd.scheduler.model.Lesson;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import java.util.List;

import static com.solvd.scheduler.terminal.scanner.TerminalInputInfo.*;

public class Main {
    public final static Logger logger = LogManager.getLogger(Main.class);

    public static void main(String[] args) throws InputException {
        List<Group> groupsAndTheirSubjectWithTimePerWeek = GroupService.gettingGroupWithSubjectImplementation();
        TerminalInputInfo.openScanner(groupsAndTheirSubjectWithTimePerWeek);
        IScheduleGenerate sg = new ScheduleGenerator(groupsAndTheirSubjectWithTimePerWeek, daysPerWeek, minLessonsPerDay, maxLessonsPerDay);
        List<Lesson> lessons = sg.compute();
        TerminalOutputInfo.presentOutput(lessons);
    }
}