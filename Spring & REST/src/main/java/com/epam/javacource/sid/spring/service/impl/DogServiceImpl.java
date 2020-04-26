package com.epam.javacource.sid.spring.service.impl;

import com.epam.javacource.sid.spring.dao.Dao;
import com.epam.javacource.sid.spring.model.Dog;
import com.epam.javacource.sid.spring.model.DogDto;
import com.epam.javacource.sid.spring.service.DogService;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

public class DogServiceImpl implements DogService {

    private Dao<Dog> dogDao;

    public DogServiceImpl(Dao<Dog> dogDao) {
        this.dogDao = dogDao;
    }

    @Override
    @Transactional
    public DogDto create(DogDto dog) {
        return dogDao.create(Dog.toEntity(dog)).toDto();
    }

    @Override
    @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
    public DogDto getOne(Integer id) {
        return dogDao.getOne(id).toDto();
    }

    @Override
    @Transactional
    public DogDto update(DogDto dog) {
        return dogDao.update(Dog.toEntity(dog)).toDto();
    }

    @Override
    @Transactional
    public void delete(Integer id) {
        dogDao.delete(id);
    }
}
