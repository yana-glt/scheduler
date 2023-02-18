package com.solvd.scheduler.model;

import java.util.HashMap;

public class Group{
    private long id;
    private String name;
    private HashMap<Subject,Integer> subjectAmountPerWeek;
    public Group(){
        subjectAmountPerWeek = new HashMap<>();
    }

    public long getId(){
        return id;
    }

    public void setId(long id){
        this.id = id;
    }

    public String getName(){
        return name;
    }

    public void setName(String name){
        this.name = name;
    }

    public HashMap<Subject, Integer> getSubjectAmountPerWeek(){
        return subjectAmountPerWeek;
    }

    public void setSubjectAmountPerWeek(Subject subject){
        subjectAmountPerWeek.put(subject, subject.getAmountPerWeek());
    }

    @Override
    public String toString(){
        return "Group{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}
