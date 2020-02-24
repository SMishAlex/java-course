package com.epam.javacource.sid.spring.controller;

import com.epam.javacource.sid.spring.dao.Dao;
import com.epam.javacource.sid.spring.model.DogDto;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/dog")
public class DogController {

    private final Dao<DogDto> dogDao;

    public DogController(Dao<DogDto> dogDao) {
        this.dogDao = dogDao;
    }

    @GetMapping("/{id}")
    public DogDto getDog(@PathVariable("id") Integer id) {
        return dogDao.getOne(id);
    }

    @PostMapping
    public DogDto createDog(@Valid @RequestBody DogDto dog) {
        return dogDao.create(dog);
    }

    @PutMapping("/{id}")
    public DogDto updateDog(@PathVariable("id") Integer id, @Valid @RequestBody DogDto dog) {
        dog.setId(id);
        return dogDao.update(dog);
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