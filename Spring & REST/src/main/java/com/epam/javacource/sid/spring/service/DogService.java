package com.epam.javacource.sid.spring.service;

import com.epam.javacource.sid.spring.dao.Dao;
import com.epam.javacource.sid.spring.dao.JdbcConnectionHolder;
import com.epam.javacource.sid.spring.model.Dog;
import com.epam.javacource.sid.spring.model.DogDto;

public class DogService {

    private Dao<Dog> dogDao;
    private JdbcConnectionHolder jdbcConnectionHolder;

    public DogService(Dao<Dog> dogDao) {
        this.dogDao = dogDao;
    }

    public DogService(Dao<Dog> dogDao, JdbcConnectionHolder jdbcConnectionHolder) {
        this.dogDao = dogDao;
        this.jdbcConnectionHolder = jdbcConnectionHolder;
    }

    public DogDto create(DogDto dog) {
        jdbcConnectionHolder.startTransaction();
        final Dog saved;
        try {
            saved = dogDao.create(Dog.toEntity(dog));
            jdbcConnectionHolder.commitTransaction();
        } catch (Exception e) {
            jdbcConnectionHolder.rollbackTransaction();
            throw e;
        }
        return saved.toDto();
    }

    public DogDto getOne(Integer id) {
        jdbcConnectionHolder.startTransaction();
        final Dog found;
        try {
            found = dogDao.getOne(id);
            jdbcConnectionHolder.commitTransaction();
        } catch (Exception e) {
            jdbcConnectionHolder.rollbackTransaction();
            throw e;
        }
        return found.toDto();
    }

    public DogDto update(DogDto dog) {
        jdbcConnectionHolder.startTransaction();
        final Dog updated;
        try {
            updated = dogDao.update(Dog.toEntity(dog));
            jdbcConnectionHolder.commitTransaction();
        } catch (Exception e) {
            jdbcConnectionHolder.rollbackTransaction();
            throw e;
        }
        return updated.toDto();
    }

    public void delete(Integer id) {
        jdbcConnectionHolder.startTransaction();
        try {
            dogDao.delete(id);
            jdbcConnectionHolder.commitTransaction();
        } catch (Exception e) {
            jdbcConnectionHolder.rollbackTransaction();
            throw e;
        }
    }
}
