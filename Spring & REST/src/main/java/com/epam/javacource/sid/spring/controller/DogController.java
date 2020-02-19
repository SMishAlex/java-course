package com.epam.javacource.sid.spring.controller;

import com.epam.javacource.sid.spring.dao.DogDao;
import com.epam.javacource.sid.spring.exceptions.BeanValidationException;
import com.epam.javacource.sid.spring.model.DogDto;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/dog")
public class DogController {

    private final DogDao dogDao;

    public DogController(DogDao dogDao) {
        this.dogDao = dogDao;
    }

    @GetMapping("/{id}")
    public DogDto getDog(@PathVariable("id") Integer id) {
        return dogDao.getOne(id);
    }

    @PostMapping
    public DogDto createDog(@Valid @RequestBody DogDto dog, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            throw new BeanValidationException(bindingResult.toString());
        }
        return dogDao.create(dog);
    }

    @PutMapping("/{id}")
    public DogDto updateDog(@PathVariable("id") Integer id, @Valid @RequestBody DogDto dog, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            throw new BeanValidationException(bindingResult.toString());
        }
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