package com.solvd.scheduler.service.implementation;

import com.solvd.scheduler.dao.interfaces.ITeacherDAO;
import com.solvd.scheduler.model.Teacher;
import com.solvd.scheduler.service.interfaces.ITeacherService;
import com.solvd.scheduler.util.MyBatisDAOFactory;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class TeacherService  implements ITeacherService {
    private final static Logger logger = LogManager.getLogger(TeacherService.class);
    private static final SqlSessionFactory SESSION_FACTORY = MyBatisDAOFactory.getSqlSessionFactory();

    @Override
    public Teacher getRecordById(long id) {
        try(SqlSession sqlSession = SESSION_FACTORY.openSession()) {
            ITeacherDAO teacherDAO = sqlSession.getMapper(ITeacherDAO.class);
            Teacher teacher = teacherDAO.getRecordById(1);
            logger.info("The Teacher class object with id " + id + "was retrieved from the database");
            return teacher;
        }
    }
}