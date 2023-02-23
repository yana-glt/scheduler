package com.solvd.scheduler.dao.interfaces;

public interface IBaseDAO<T>{

    T getRecordById(long id);

    void insertRecord(T entity);

    void updateRecord(T entity);

    void deleteRecord(T entity);

}
