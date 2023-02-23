package com.solvd.scheduler.dao.interfaces;

import com.solvd.scheduler.model.Group;
import java.util.List;

public interface IGroupDAO extends IBaseDAO<Group>{
    @Override
    Group getRecordById(long id);

    @Override
    void insertRecord(Group entity);

    @Override
    void updateRecord(Group entity);

    @Override
    void deleteRecord(Group entity);

    List<Group> getGroupsWithSubjects();

    List<Group> getGroupsWithoutSubjects();
}