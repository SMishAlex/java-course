package com.epam.javacource.sid.spring.controller;

import com.epam.javacource.sid.spring.dao.Dao;
import com.epam.javacource.sid.spring.model.Dog;
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
    public Dog getDog(@PathVariable("id") Integer id) {
        return dogDao.getOne(id);
    }

    @PostMapping
    public Dog createDog(@Valid @RequestBody Dog dog) {
        return dogDao.create(dog);
    }

    @PutMapping("/{id}")
    public Dog updateDog(@PathVariable("id") Integer id, @Valid @RequestBody Dog dog) {
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