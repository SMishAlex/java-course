package com.epam.javacource.sid.spring.model;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Value;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;
import java.time.LocalDate;

@Value
@JsonDeserialize(builder = DogDto.DogDtoBuilder.class)
@Builder(toBuilder = true)
@AllArgsConstructor
public class DogDto {
    @Positive
    Integer id;
    @NotNull
    @Size(max = 100, min = 1)
    String name;
    @Past
    LocalDate dateOfBirth;
    @NotNull
    @Positive
    Long height;
    @NotNull
    @Positive
    Long weight;

    @JsonPOJOBuilder(withPrefix = "")
    public static class DogDtoBuilder {
    }
}
