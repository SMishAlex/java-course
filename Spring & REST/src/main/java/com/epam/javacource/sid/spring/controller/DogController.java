package com.epam.javacource.sid.spring.controller;

import com.epam.javacource.sid.spring.dao.Dao;
import com.epam.javacource.sid.spring.model.Dog;
import com.epam.javacource.sid.spring.model.DogDto;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@ResponseBody
@RequestMapping("/dog")
public class DogController {

    private final Dao<Dog> dogDao;

    public DogController(Dao<Dog> dogDao) {
        this.dogDao = dogDao;
    }

    @GetMapping("/{id}")
    public DogDto getDog(@PathVariable("id") Integer id) {
        return dogDao.getOne(id).toDto();
    }

    @PostMapping
    public DogDto createDog(@Valid @RequestBody DogDto dog) {
        return dogDao.create(Dog.toEntity(dog)).toDto();
    }

    @PutMapping("/{id}")
    public DogDto updateDog(@PathVariable("id") Integer id, @Valid @RequestBody DogDto dog) {
        final DogDto dtoWithIdFromPath = dog.toBuilder().id(id).build();
        return dogDao.update(Dog.toEntity(dtoWithIdFromPath)).toDto();
    }

    @DeleteMapping("/{id}")
    public void deleteDog(@PathVariable("id") Integer id) {
        dogDao.delete(id);
    }

    @GetMapping("/hello")
    public String helloEndpoint() {
        return "Hello!";
    }
}