package com.solvd.scheduler.service.implementation;

import com.solvd.scheduler.dao.interfaces.IGroupDAO;
import com.solvd.scheduler.model.Group;
import com.solvd.scheduler.service.interfaces.IGroupService;
import com.solvd.scheduler.terminal.InfoFromDB;
import com.solvd.scheduler.util.MyBatisDAOFactory;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;

public class GroupService implements IGroupService {
    private final static Logger LOGGER = LogManager.getLogger(GroupService.class);
    private static final SqlSessionFactory SESSION_FACTORY = MyBatisDAOFactory.getSqlSessionFactory();

    @Override
    public List<Group> groupsAndTheirSubjectWithTimePerWeek() {
        try (SqlSession sqlSession = SESSION_FACTORY.openSession()) {
            IGroupDAO IGroupDAO = sqlSession.getMapper(IGroupDAO.class);
            List<Group> groupsAndTheirSubjectWithTimePerWeek = IGroupDAO.getGroupsWithSubjects();
            LOGGER.info("The list of groups (with subjects) was retrieved from the database.");
            return groupsAndTheirSubjectWithTimePerWeek;
        }
    }

    @Override
    public List<Group> getGroupsWithoutSubjects() {
        try (SqlSession sqlSession = SESSION_FACTORY.openSession()) {
            IGroupDAO IGroupDAO = sqlSession.getMapper(IGroupDAO.class);
            List<Group> groupsWithoutSubjects = IGroupDAO.getGroupsWithoutSubjects();
            LOGGER.info("The list of groups (without subjects) was retrieved from the database.");
            return groupsWithoutSubjects;
        }
    }

    public static boolean isListOfGroupsEmpty(List<Group> groups) {
        if (groups.isEmpty()) {
            LOGGER.debug("The resulting list of groups is empty.");
            return true;
        } else {
            LOGGER.debug("Database contains groups.");
            return false;
        }
    }

    public static List<Group> gettingGroupWithSubjectImplementation() {
        IGroupService groupService = new GroupService();
        List<Group> groupsAndTheirSubjectWithTimePerWeek = groupService.groupsAndTheirSubjectWithTimePerWeek();
        if (!GroupService.isListOfGroupsEmpty(groupsAndTheirSubjectWithTimePerWeek)) {
            int maxAmountOfHoursInDB = groupsAndTheirSubjectWithTimePerWeek.stream().map(p -> p.getSubjectsAsList().size()).max(Integer::compareTo).orElse(0);
            if (maxAmountOfHoursInDB == 0) {
                LOGGER.error("None of subjects in the database has a number of hours greater than or equal to 1");
                System.out.println("None of subjects in the database has a number of hours greater than or equal to 1");
                System.exit(-1);
            } else if (maxAmountOfHoursInDB <= 40) {
                InfoFromDB.printDataAboutGroupsWithSubjects(groupsAndTheirSubjectWithTimePerWeek);
                return groupsAndTheirSubjectWithTimePerWeek;
            } else {
                LOGGER.error(String.format("At least one group in the database has %d lessons, which is more than the maximum possible 40 hours.", maxAmountOfHoursInDB));
                System.out.println("Calculated minimal amount of days required to allocate all lessons for some groups exceeds " +
                        "max number of working days in the week [5 days].\nFor this reason schedule can't be generated. " +
                        "Please go to DB and check if each group has less or equal to 40 hours of lessons per week");
                System.exit(-1);
            }
        } else {
            List<Group> groupsWithoutSubjects = groupService.getGroupsWithoutSubjects();
            if (!GroupService.isListOfGroupsEmpty(groupsWithoutSubjects)) {
                LOGGER.error("There is not enough data in the database for scheduling. Subjects for groups are not specified.");
                System.out.println("There is not enough data in the database for scheduling. Subjects for groups are not specified." +
                        "\nPlease add subjects for the groups below and try again.");
                InfoFromDB.printDataAboutGroupsName(groupsWithoutSubjects);
                System.exit(-1);
            } else {
                LOGGER.error("There is no data in the database for scheduling.");
                System.out.println("There is no data in the database for scheduling. " +
                        "\nPlease add data and try again.");
                System.exit(-1);
            }
        }
        return null;
    }
}