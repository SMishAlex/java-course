package com.epam.javacource.sid.spring.dao;

public interface Dao<T> {
    T create(T entity);

    T getOne(Integer id);

    T update(T entity);

    void delete(Integer id);
}
