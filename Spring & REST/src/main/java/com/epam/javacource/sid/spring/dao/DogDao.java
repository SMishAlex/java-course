package com.epam.javacource.sid.spring.dao;

import com.epam.javacource.sid.spring.exceptions.ResourceNotFoundException;
import com.epam.javacource.sid.spring.model.DogDto;
import org.springframework.stereotype.Repository;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

import static java.util.Optional.ofNullable;

public class DogDao implements Dao<DogDto> {

    static Map<Integer, DogDto> inMemoryDogs = new ConcurrentHashMap<>();
    static AtomicInteger idSequence = new AtomicInteger(0);

    @Override
    public DogDto create(DogDto dog) {
        Integer id = idSequence.incrementAndGet();
        dog.setId(id);
        inMemoryDogs.put(id, dog);
        return dog;
    }

    @Override
    public DogDto getOne(Integer id) {
        return ofNullable(inMemoryDogs.get(id))
                .orElseThrow(() -> new ResourceNotFoundException("Seems like your dog is gone."));
    }

    @Override
    public DogDto update(DogDto dog) {
        inMemoryDogs.put(dog.getId(), dog);
        return dog;
    }

    @Override
    public void delete(Integer id) {
        inMemoryDogs.remove(id);
    }
}
