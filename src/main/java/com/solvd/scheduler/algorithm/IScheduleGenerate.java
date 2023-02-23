package com.solvd.scheduler.algorithm;

import com.solvd.scheduler.model.Lesson;

import java.util.List;

public interface IScheduleGenerate {
    List<Lesson> compute();
}
