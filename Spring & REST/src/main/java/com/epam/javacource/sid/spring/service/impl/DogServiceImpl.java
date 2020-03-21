package com.epam.javacource.sid.spring.service.impl;

import com.epam.javacource.sid.spring.aop.annotations.IDontUseTryWithResourcesPleaseLetMeBeTransactional;
import com.epam.javacource.sid.spring.dao.Dao;
import com.epam.javacource.sid.spring.model.Dog;
import com.epam.javacource.sid.spring.model.DogDto;
import com.epam.javacource.sid.spring.service.DogService;

public class DogServiceImpl implements DogService {

    private Dao<Dog> dogDao;

    public DogServiceImpl(Dao<Dog> dogDao) {
        this.dogDao = dogDao;
    }

    @Override
    @IDontUseTryWithResourcesPleaseLetMeBeTransactional
    public DogDto create(DogDto dog) {
        return dogDao.create(Dog.toEntity(dog)).toDto();
    }

    @Override
    @IDontUseTryWithResourcesPleaseLetMeBeTransactional
    public DogDto getOne(Integer id) {
        return dogDao.getOne(id).toDto();
    }

    @Override
    @IDontUseTryWithResourcesPleaseLetMeBeTransactional
    public DogDto update(DogDto dog) {
        return dogDao.update(Dog.toEntity(dog)).toDto();
    }

    @Override
    @IDontUseTryWithResourcesPleaseLetMeBeTransactional
    public void delete(Integer id) {
        dogDao.delete(id);
    }
}
