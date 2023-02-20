package com.solvd.scheduler.service.implementation;

import com.solvd.scheduler.dao.interfaces.ISubjectDAO;
import com.solvd.scheduler.model.Subject;
import com.solvd.scheduler.service.interfaces.ISubjectService;
import com.solvd.scheduler.util.MyBatisDAOFactory;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class SubjectService implements ISubjectService {
    private final static Logger logger = LogManager.getLogger(SubjectService.class);
    private static final SqlSessionFactory SESSION_FACTORY = MyBatisDAOFactory.getSqlSessionFactory();

    @Override
    public Subject getRecordById(long id) {
        try (SqlSession sqlSession = SESSION_FACTORY.openSession()) {
            ISubjectDAO subjectDAO = sqlSession.getMapper(ISubjectDAO.class);
            Subject subject = subjectDAO.getRecordById(1);
            logger.info("The Subject class object with id " + id + "was retrieved from the database");
            return subject;
        }
    }
}