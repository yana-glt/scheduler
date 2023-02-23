package com.solvd.scheduler;

import com.solvd.scheduler.model.Group;
import com.solvd.scheduler.service.implementation.GroupService;
import com.solvd.scheduler.service.interfaces.IGroupService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import java.util.List;
import java.util.stream.Collectors;

public class Main {
    private final static Logger logger = LogManager.getLogger(Main.class);
    private static final int NUM_DAYS = 5;
    private static final int MIN_LESSONS = 2;
    private static final int MAX_LESSONS = 8;

    public static void main(String[] args) {
        IGroupService groupService = new GroupService();
        List<Group> groupsAndTheirSubjectWithTimePerWeek = groupService.groupsAndTheirSubjectWithTimePerWeek();
        if(GroupService.checkData(groupsAndTheirSubjectWithTimePerWeek)){
            //InfoFromDB.printDataAboutGroupsWithSubjects(groupsAndTheirSubjectWithTimePerWeek);
            for (Group g : groupsAndTheirSubjectWithTimePerWeek) {
                System.out.println(g.toString());
                System.out.println(g.getSubjectAmountPerWeek().keySet().stream().collect(Collectors.toList()));
            }
        }else{
            List<Group> groupsWithoutSubjects = groupService.getGroupsWithoutSubjects();
            if(GroupService.checkData(groupsWithoutSubjects)){
                logger.error("There is not enough data in the database for scheduling." +
                        "\nSubjects for groups are not specified.");
                System.out.println("There is not enough data in the database for scheduling. " +
                        "\nPlease change the data in the database for the groups below and try again.");
                //InfoFromDB.printDataAboutGroupsWithoutSubjects(groupsWithoutSubjects);
                for (Group g : groupsWithoutSubjects) {
                    System.out.println(g.getName());
                }
            }else{
                logger.error("There is no data in the database for scheduling.");
                System.out.println("There is no data in the database for scheduling. " +
                        "\nPlease add data and try again.");
                System.exit(-1);
            }
        }
    }
}