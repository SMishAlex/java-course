package com.epam.javacource.sid.spring.controller;

import com.epam.javacource.sid.spring.model.DogDto;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

import static java.util.Optional.ofNullable;

@RestController
@RequestMapping("/dog")
public class DogController {

    static Map<Integer, DogDto> inMemoryDogs = new ConcurrentHashMap<>();
    static AtomicInteger idSequence = new AtomicInteger(0);

    @GetMapping("/{id}")
    public DogDto getDog(@PathVariable("id") Integer id) {
        return ofNullable(inMemoryDogs.get(id))
                .orElseThrow(() -> new RuntimeException("Seems like your dog is gone."));
    }

    @PostMapping
    public DogDto createDog(@RequestBody DogDto dog) {
        Integer id = idSequence.incrementAndGet();
        dog.setId(id);
        inMemoryDogs.put(id, dog);
        return dog;
    }

    @PutMapping("/{id}")
    public DogDto updateDog(@PathVariable("id") Integer id, @RequestBody DogDto dog) {
        inMemoryDogs.put(id, dog);
        return dog;
    }

    @DeleteMapping("/{id}")
    public void deleteDog(@PathVariable("id") Integer id) {
        inMemoryDogs.remove(id);
    }

    @GetMapping("/hello")
    public String helloEndpoint() {
        return "Hello!";
    }
}