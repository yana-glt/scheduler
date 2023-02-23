package com.solvd.scheduler;

import com.solvd.scheduler.model.Group;
import com.solvd.scheduler.service.implementation.GroupService;
import com.solvd.scheduler.service.interfaces.IGroupService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import java.util.List;
import java.util.stream.Collectors;

public class Main{
    private final static Logger logger = LogManager.getLogger(Main.class.getName());

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
            logger.error("There is not enough data in the database for scheduling.");
            System.out.println("There is not enough data in the database for scheduling. " +
                    "\nPlease change the data in the database for the groups below and try again.");
            for (Group g : groupsWithoutSubjects) {
                System.out.println(g.getName());
            }
            //InfoFromDB.printDataAboutGroupsWithoutSubjects(groupsWithoutSubjects);
            System.exit(-1);
        }
    }
}