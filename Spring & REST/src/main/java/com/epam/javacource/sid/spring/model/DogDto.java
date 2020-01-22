package com.epam.javacource.sid.spring.model;

import javax.validation.constraints.Past;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;
import java.time.LocalDate;
import java.util.Objects;

public class DogDto {
    @Positive
    private Integer id;
    @Size(max = 100, min = 1)
    private String name;
    @Past
    private LocalDate dateOfBirth;
    @Positive
    private Long height;
    @Positive
    private Long weight;

    public DogDto() {
    }

    public DogDto(Integer id, String name, LocalDate dateOfBirth, Long height, Long weight) {
        this.id = id;
        this.name = name;
        this.dateOfBirth = dateOfBirth;
        this.height = height;
        this.weight = weight;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public LocalDate getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(LocalDate dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public Long getHeight() {
        return height;
    }

    public void setHeight(Long height) {
        this.height = height;
    }

    public Long getWeight() {
        return weight;
    }

    public void setWeight(Long weight) {
        this.weight = weight;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        DogDto dogDto = (DogDto) o;
        return Objects.equals(id, dogDto.id) &&
                Objects.equals(name, dogDto.name) &&
                Objects.equals(dateOfBirth, dogDto.dateOfBirth) &&
                Objects.equals(height, dogDto.height) &&
                Objects.equals(weight, dogDto.weight);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, dateOfBirth, height, weight);
    }

    @Override
    public String toString() {
        return "DogDto{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", dateOfBirth=" + dateOfBirth +
                ", height=" + height +
                ", weight=" + weight +
                '}';
    }
}
