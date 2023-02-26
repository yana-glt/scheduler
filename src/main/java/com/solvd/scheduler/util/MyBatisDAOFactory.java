package com.solvd.scheduler.util;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.io.Reader;

public class MyBatisDAOFactory {
    private final static Logger LOGGER = LogManager.getLogger(MyBatisDAOFactory.class);
    private final static MyBatisDAOFactory myBatisDaoFactory = new MyBatisDAOFactory();
    private static SqlSessionFactory sqlSessionFactory;

    private MyBatisDAOFactory() {
        try {
            Reader reader = Resources.getResourceAsReader("mybatis-config.xml");
            sqlSessionFactory = new SqlSessionFactoryBuilder().build(reader);
        } catch (IOException e) {
            LOGGER.error("Exception while reading configuration", e);
        }
    }

    public static SqlSessionFactory getSqlSessionFactory() {
        return sqlSessionFactory;
    }
}
