package com.epam.javacource.sid.spring.controller;

import com.epam.javacource.sid.spring.model.DogDto;
import com.epam.javacource.sid.spring.service.DogService;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@ResponseBody
@RequestMapping("/dog")
public class DogController {

    private final DogService dogService;

    public DogController(DogService dogService) {
        this.dogService = dogService;
    }

    @GetMapping("/{id}")
    public DogDto getDog(@PathVariable("id") Integer id) {
        return dogService.getOne(id);
    }

    @PostMapping
    public DogDto createDog(@Valid @RequestBody DogDto dog) {
        return dogService.create(dog);
    }

    @PutMapping("/{id}")
    public DogDto updateDog(@PathVariable("id") Integer id, @Valid @RequestBody DogDto dog) {
        final DogDto dtoWithIdFromPath = dog.toBuilder().id(id).build();
        return dogService.update(dtoWithIdFromPath);
    }

    @DeleteMapping("/{id}")
    public void deleteDog(@PathVariable("id") Integer id) {
        dogService.delete(id);
    }

    @GetMapping("/hello")
    public String helloEndpoint() {
        return "Hello!";
    }
}