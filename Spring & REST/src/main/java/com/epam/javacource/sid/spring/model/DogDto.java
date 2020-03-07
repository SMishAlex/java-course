package com.epam.javacource.sid.spring.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Value;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;
import java.time.LocalDate;

@Value
@Builder
@AllArgsConstructor
public class DogDto {
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
}
