package com.solvd.scheduler.model;

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

    @Override
    public String toString(){
        return String.format("Subject{id= %d, name = %s,teacher= %s, amountPerWeek= %d}", id,
                name, teacher, amountPerWeek);

    }

}
