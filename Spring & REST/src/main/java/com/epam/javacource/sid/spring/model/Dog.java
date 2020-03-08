package com.epam.javacource.sid.spring.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;
import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Dog {
    @Positive
    private Integer id;
    @NotNull
    @Size(max = 100, min = 1)
    private String name;
    @Past
    private LocalDate dateOfBirth;
    @NotNull
    @Positive
    private Long height;
    @NotNull
    @Positive
    private Long weight;

    public DogDto toDto() {
        return DogDto.builder()
                .id(id)
                .name(name)
                .dateOfBirth(dateOfBirth)
                .height(height)
                .weight(weight)
                .build();
    }

    public static Dog toEntity(DogDto dogDto) {
        return new Dog(dogDto.getId(), dogDto.getName(),
                dogDto.getDateOfBirth(), dogDto.getHeight(), dogDto.getWeight());
    }
}
