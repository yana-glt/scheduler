package com.solvd.scheduler.service.interfaces;

public interface IBaseService <T>{

    T getRecordById(long id);

    void insertRecord(T entity);

    void updateRecord(T entity);

    void deleteRecord(T entity);
}