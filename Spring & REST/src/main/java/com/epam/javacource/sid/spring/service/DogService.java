package com.epam.javacource.sid.spring.service;

import com.epam.javacource.sid.spring.model.DogDto;

public interface DogService {
    DogDto create(DogDto dog);

    DogDto getOne(Integer id);

    DogDto update(DogDto dog);

    void delete(Integer id);
}
