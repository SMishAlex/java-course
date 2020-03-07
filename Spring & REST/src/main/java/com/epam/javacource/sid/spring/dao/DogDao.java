package com.epam.javacource.sid.spring.dao;

import com.epam.javacource.sid.spring.exceptions.ResourceNotFoundException;
import com.epam.javacource.sid.spring.model.Dog;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

import static java.util.Optional.ofNullable;

public class DogDao implements Dao<Dog> {

    static Map<Integer, Dog> inMemoryDogs = new ConcurrentHashMap<>();
    static AtomicInteger idSequence = new AtomicInteger(0);

    @Override
    public Dog create(Dog dog) {
        Integer id = idSequence.incrementAndGet();
        dog.setId(id);
        inMemoryDogs.put(id, dog);
        return dog;
    }

    @Override
    public Dog getOne(Integer id) {
        return ofNullable(inMemoryDogs.get(id))
                .orElseThrow(() -> new ResourceNotFoundException("Seems like your dog is gone."));
    }

    @Override
    public Dog update(Dog dog) {
        inMemoryDogs.put(dog.getId(), dog);
        return dog;
    }

    @Override
    public void delete(Integer id) {
        inMemoryDogs.remove(id);
    }
}
