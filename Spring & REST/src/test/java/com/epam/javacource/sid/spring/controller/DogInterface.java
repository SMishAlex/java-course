package com.epam.javacource.sid.spring.controller;

public interface DogInterface {
    Integer getId();

    String getName();

    java.time.LocalDate getDateOfBirth();

    Long getHeight();

    Long getWeight();

    void setId(Integer id);

    void setName(String name);

    void setDateOfBirth(java.time.LocalDate dateOfBirth);

    void setHeight(Long height);

    void setWeight(Long weight);
}
