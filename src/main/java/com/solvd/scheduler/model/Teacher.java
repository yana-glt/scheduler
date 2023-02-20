package com.solvd.scheduler.model;

public class Teacher{

    private long id;

    private String name;

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

    @Override
    public String toString(){
        return String.format("Teacher{id= %d, name= %s}", id, name);
    }

}
