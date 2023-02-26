package com.solvd.scheduler.model;

import java.util.Objects;

public class Subject{
    private long id;
    private String name;
    private Teacher teacher;
    private int amountPerWeek;

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

    public Teacher getTeacher(){
        return teacher;
    }

    public void setTeacher(Teacher teacher){
        this.teacher = teacher;
    }

    public int getAmountPerWeek(){
        return amountPerWeek;
    }

    public void setAmountPerWeek(int amountPerWeek){
        this.amountPerWeek = amountPerWeek;
    }

//    @Override
//    public boolean equals(Object o) {
//        if (this == o) return true;
//        if (!(o instanceof Subject subject)) return false;
//        return id == subject.id && amountPerWeek == subject.amountPerWeek && name.equals(subject.name)
//                && Objects.equals(teacher, subject.teacher);
//    }
//
//    @Override
//    public int hashCode() {
//        return Objects.hash(id, name, teacher, amountPerWeek);
//    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Subject subject)) return false;
        return id == subject.id && name.equals(subject.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name);
    }

    @Override
    public String toString(){
        return String.format("Subject{id= %d, name = %s,teacher= %s, amountPerWeek= %d}", id,
                name, teacher, amountPerWeek);
    }
}