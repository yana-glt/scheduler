package com.solvd.scheduler.service.implementation;

import com.solvd.scheduler.dao.interfaces.IGroupDAO;
import com.solvd.scheduler.model.Group;
import com.solvd.scheduler.service.interfaces.IGroupService;
import com.solvd.scheduler.util.MyBatisDAOFactory;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;

public class GroupService implements IGroupService {
    private final static Logger logger = LogManager.getLogger(GroupService.class);
    private static final SqlSessionFactory SESSION_FACTORY = MyBatisDAOFactory.getSqlSessionFactory();
    @Override
    public Group getRecordById(long id) {
        try(SqlSession sqlSession = SESSION_FACTORY.openSession()) {
            IGroupDAO groupDAO = sqlSession.getMapper(IGroupDAO.class);
            Group group = groupDAO.getRecordById(1);
            return group;
        }
    }

    @Override
    public List<Group> groupsAndTheirSubjectWithTimePerWeek() {
        SqlSessionFactory sqlSessionFactory = MyBatisDAOFactory.getSqlSessionFactory();
        SqlSession sqlSession = sqlSessionFactory.openSession();
        IGroupDAO IGroupDAO = sqlSession.getMapper(IGroupDAO.class);
        List<Group> groupsAndTheirSubjectWithTimePerWeek = IGroupDAO.getGroupsWithSubjects();
        return groupsAndTheirSubjectWithTimePerWeek;
    }

    @Override
    public void insertRecord(Group entity) {

    }

    @Override
    public void updateRecord(Group entity) {

    }

    @Override
    public void deleteRecord(Group entity) {

    }
}