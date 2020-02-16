package com.epam.javacource.sid.spring.controller;

import com.epam.javacource.sid.spring.model.DogDto;
import io.restassured.http.ContentType;
import io.restassured.mapper.ObjectMapperType;
import org.springframework.http.HttpStatus;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.time.LocalDate;

import static io.restassured.RestAssured.given;

public class DogControllerTest {

    private final static String HOST = "http://localhost:8080";

    private DogDto getDog1() {
        return new DogDto(null, "Dog1Name", LocalDate.now(), 10L, 10L);
    }

    private DogDto getUpdatedDog(Integer dogId) {
        return new DogDto(dogId, "Updated Dog1Name", LocalDate.now(), 15L, 15L);
    }


    @Test
    public void testCreatingDog() {
        given()
                .contentType(ContentType.JSON)
                .body(getDog1(), ObjectMapperType.JACKSON_2)
                .when()
                .post(HOST + "/dog")
                .then()
                .statusCode(HttpStatus.OK.value());
    }

    @Test
    public void testGettingCreatedDogById() {
        DogDto dog1 = given()
                .contentType(ContentType.JSON)
                .body(getDog1(), ObjectMapperType.JACKSON_2)
                .post(HOST + "/dog")
                .as(DogDto.class);

        DogDto getDog = given()
                .get(HOST + "/dog/{id}", dog1.getId())
                .as(DogDto.class);

        Assert.assertEquals(dog1, getDog);
    }

    @Test
    public void testUpdatingCreatedDogById() {
        DogDto dog1 = given()
                .contentType(ContentType.JSON)
                .body(getDog1(), ObjectMapperType.JACKSON_2)
                .post(HOST + "/dog")
                .as(DogDto.class);


        DogDto updatedDog1 = getUpdatedDog(dog1.getId());
        DogDto updatedDog1Response = given()
                .contentType(ContentType.JSON)
                .body(updatedDog1, ObjectMapperType.JACKSON_2)
                .put(HOST + "/dog/{id}", dog1.getId())
                .as(DogDto.class);

        Assert.assertEquals(updatedDog1, updatedDog1Response);

        DogDto getDog = given()
                .get(HOST + "/dog/{id}", dog1.getId())
                .as(DogDto.class);

        Assert.assertEquals(getDog, updatedDog1Response);
    }

    @Test
    public void testNegativeCreatingDog() {
        DogDto validDog = getDog1();
        validDog.setHeight(-10L);
        given()
                .contentType(ContentType.JSON)
                .body(validDog,
                        ObjectMapperType.JACKSON_2)
                .when()
                .post(HOST + "/dog")
                .then()
                .statusCode(HttpStatus.BAD_REQUEST.value());
    }
}