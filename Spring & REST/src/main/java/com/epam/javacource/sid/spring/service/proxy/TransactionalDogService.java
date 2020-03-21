package com.epam.javacource.sid.spring.service.proxy;

import com.epam.javacource.sid.spring.dao.JdbcConnectionHolder;
import com.epam.javacource.sid.spring.model.DogDto;
import com.epam.javacource.sid.spring.service.DogService;

public class TransactionalDogService implements DogService {

    private final DogService dogServiceImplementation;
    private final JdbcConnectionHolder jdbcConnectionHolder;

    public TransactionalDogService(DogService dogServiceImplementation, JdbcConnectionHolder jdbcConnectionHolder) {
        this.dogServiceImplementation = dogServiceImplementation;
        this.jdbcConnectionHolder = jdbcConnectionHolder;
    }

    @Override
    public DogDto create(DogDto dog) {
        jdbcConnectionHolder.startTransaction();
        try {
            final DogDto result = dogServiceImplementation.create(dog);
            jdbcConnectionHolder.commitTransaction();
            return result;
        } catch (Exception e) {
            jdbcConnectionHolder.rollbackTransaction();
            throw e;
        } finally {
            jdbcConnectionHolder.closeConnection();
        }
    }

    @Override
    public DogDto getOne(Integer id) {
        jdbcConnectionHolder.startTransaction();
        try {
            final DogDto result = dogServiceImplementation.getOne(id);
            jdbcConnectionHolder.commitTransaction();
            return result;
        } catch (Exception e) {
            jdbcConnectionHolder.rollbackTransaction();
            throw e;
        } finally {
            jdbcConnectionHolder.closeConnection();
        }
    }

    @Override
    public DogDto update(DogDto dog) {
        jdbcConnectionHolder.startTransaction();
        try {
            final DogDto updated = dogServiceImplementation.update(dog);
            jdbcConnectionHolder.commitTransaction();
            return updated;
        } catch (Exception e) {
            jdbcConnectionHolder.rollbackTransaction();
            throw e;
        } finally {
            jdbcConnectionHolder.closeConnection();
        }
    }

    @Override
    public void delete(Integer id) {
        jdbcConnectionHolder.startTransaction();
        try {
            dogServiceImplementation.delete(id);
            jdbcConnectionHolder.commitTransaction();
        } catch (Exception e) {
            jdbcConnectionHolder.rollbackTransaction();
            throw e;
        } finally {
            jdbcConnectionHolder.closeConnection();
        }
    }
}
