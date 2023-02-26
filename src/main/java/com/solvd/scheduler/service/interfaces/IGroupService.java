package com.solvd.scheduler.service.interfaces;

import com.solvd.scheduler.model.Group;
import java.util.List;

public interface IGroupService {

    List<Group> groupsAndTheirSubjectWithTimePerWeek();

    List<Group> getGroupsWithoutSubjects();
}
