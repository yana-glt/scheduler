package com.solvd.scheduler;

import com.solvd.scheduler.algorithm.IScheduleGenerate;
import com.solvd.scheduler.algorithm.ScheduleGenerator;
import com.solvd.scheduler.exception.InputException;
import com.solvd.scheduler.model.Group;
import com.solvd.scheduler.terminal.TerminalInputInfo;
import com.solvd.scheduler.service.implementation.GroupService;
import com.solvd.scheduler.terminal.TerminalOutputInfo;
import static com.solvd.scheduler.terminal.TerminalInputInfo.*;
import com.solvd.scheduler.model.Lesson;

import java.util.List;

public class Main {

    public static void main(String[] args) throws InputException {
        List<Group> groupsAndTheirSubjectWithTimePerWeek = GroupService.gettingGroupWithSubjectImplementation();
        TerminalInputInfo.openScanner(groupsAndTheirSubjectWithTimePerWeek);
        IScheduleGenerate sg = new ScheduleGenerator(groupsAndTheirSubjectWithTimePerWeek, daysPerWeek, minLessonsPerDay, maxLessonsPerDay);
        List<Lesson> lessons = sg.compute();
        TerminalOutputInfo.presentOutput(lessons);
    }
}
