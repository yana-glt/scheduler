package com.solvd.scheduler.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Group {
    private final HashMap<Subject, Integer> subjectAmountPerWeek;
    private long id;
    private String name;

    public Group() {
        subjectAmountPerWeek = new HashMap<>();
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public HashMap<Subject, Integer> getSubjectAmountPerWeek() {
        return subjectAmountPerWeek;
    }

    public void setSubjectAmountPerWeek(Subject subject) {
        subjectAmountPerWeek.put(subject, subject.getAmountPerWeek());
    }

    public List<Subject> getSubjectsAsList() {
        ArrayList<Subject> subjectsTemp = new ArrayList<>();
        for (Map.Entry<Subject, Integer> subjectEntry : subjectAmountPerWeek.entrySet()) {
            for (int i = 0; i < subjectEntry.getValue(); i++) {
                subjectsTemp.add(subjectEntry.getKey());
            }
        }
        return subjectsTemp;
    }

    @Override
    public String toString() {
        return String.format("Group{id = %d, name= %s}", id, name);
    }
}
