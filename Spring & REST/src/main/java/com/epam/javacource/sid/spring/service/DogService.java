package com.epam.javacource.sid.spring.service;

import com.epam.javacource.sid.spring.dao.Dao;
import com.epam.javacource.sid.spring.model.Dog;
import com.epam.javacource.sid.spring.model.DogDto;

public class DogService {

    private Dao<Dog> dogDao;

    public DogService(Dao<Dog> dogDao) {
        this.dogDao = dogDao;
    }

    public DogDto create(DogDto dog) {
        return dogDao.create(Dog.toEntity(dog)).toDto();
    }

    public DogDto getOne(Integer id) {
        return dogDao.getOne(id).toDto();
    }

    public DogDto update(DogDto dog) {
        return dogDao.update(Dog.toEntity(dog)).toDto();
    }

    public void delete(Integer id) {
        dogDao.delete(id);
    }
}
