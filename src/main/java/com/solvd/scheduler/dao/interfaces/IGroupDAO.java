package com.solvd.scheduler.dao.interfaces;

import com.solvd.scheduler.model.Group;

import java.util.List;

public interface IGroupDAO {

    List<Group> getGroupsWithSubjects();

    List<Group> getGroupsWithoutSubjects();
}
