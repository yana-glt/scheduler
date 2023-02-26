package com.solvd.scheduler.dao.interfaces;

import com.solvd.scheduler.model.Subject;

public interface ISubjectDAO extends IBaseDAO<Subject>{

    @Override
    Subject getRecordById(long id);

    @Override
    void insertRecord(Subject entity);

    @Override
    void updateRecord(Subject entity);

    @Override
    void deleteRecord(Subject entity);

}
