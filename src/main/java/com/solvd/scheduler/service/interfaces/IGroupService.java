package com.solvd.scheduler.service.interfaces;

import com.solvd.scheduler.model.Group;
import java.util.List;

public interface IGroupService extends IBaseService <Group> {

    List<Group> groupsAndTheirSubjectWithTimePerWeek();
}