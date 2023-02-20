package com.solvd.scheduler;

import com.solvd.scheduler.dao.interfaces.IGroupDAO;
import com.solvd.scheduler.model.Group;
import com.solvd.scheduler.util.MyBatisDAOFactory;
import com.solvd.scheduler.util.Schedule;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;

public class Main{

    private final static Logger logger = LogManager.getLogger(Main.class.getName());

    public static void main(String[] args){
        logger.info("Logger verification");
        SqlSessionFactory sqlSessionFactory = MyBatisDAOFactory.getSqlSessionFactory();
        SqlSession sqlSession = sqlSessionFactory.openSession();
        IGroupDAO IGroupDAO = sqlSession.getMapper(IGroupDAO.class);
        //Group group = IGroupDAO.getRecordById(5);
        List<Group> groupsAndTheirSubjectWithTimePerWeek = IGroupDAO.getGroupsWithSubjects();
        /*for (Group g : groupsAndTheirSubjectWithTimePerWeek){
            System.out.println(g.toString());
            System.out.println(g.getSubjectAmountPerWeek().keySet().stream().collect(Collectors.toList()));
        }*/
        System.out.println(Schedule.minDaysToSatisfySchedule(groupsAndTheirSubjectWithTimePerWeek));
    }

}