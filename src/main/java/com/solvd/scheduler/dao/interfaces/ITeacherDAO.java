package com.solvd.scheduler.dao.interfaces;

import com.solvd.scheduler.model.Teacher;

public interface ITeacherDAO extends IBaseDAO<Teacher>{
    @Override
    Teacher getRecordById(long id);

    @Override
    void insertRecord(Teacher entity);

    @Override
    void updateRecord(Teacher entity);

    @Override
    void deleteRecord(Teacher entity);
}
