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

    public static void main(String[] args){
        logger.info("Check logger");
        IGroupService groupService = new GroupService();
        List<Group> groupsAndTheirSubjectWithTimePerWeek = groupService.groupsAndTheirSubjectWithTimePerWeek();
        for (Group g : groupsAndTheirSubjectWithTimePerWeek){
            System.out.println(g.toString());
            System.out.println(g.getSubjectAmountPerWeek().keySet().stream().collect(Collectors.toList()));
        }
    }
}