package com.epam.javacource.sid.spring.controller;

import com.epam.javacource.sid.spring.model.DogDto;
import io.restassured.http.ContentType;
import io.restassured.mapper.ObjectMapperType;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.time.LocalDate;

import static io.restassured.RestAssured.given;

public class DogControllerTest {

    private final static String HOST = "http://localhost:8080";

    @Test
    public void testCreatingDog() {
        given()
                .contentType(ContentType.JSON)
                .body(new DogDto(null, "Dog1Name", LocalDate.now(), 10L, 10L),
                        ObjectMapperType.JACKSON_2)
                .when()
                .post(HOST + "/dog")
                .then()
                .statusCode(200);
    }

    @Test
    public void testGettingCreatedDogById() {
        DogDto dog1 = given()
                .contentType(ContentType.JSON)
                .body(new DogDto(null, "Dog1Name", LocalDate.now(), 10L, 10L),
                        ObjectMapperType.JACKSON_2)
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
                .body(new DogDto(null, "Dog1Name", LocalDate.now(), 10L, 10L),
                        ObjectMapperType.JACKSON_2)
                .post(HOST + "/dog")
                .as(DogDto.class);


        DogDto updatedDog1 = new DogDto(dog1.getId(), "Updated Dog1Name", LocalDate.now(), 15L, 15L);
        DogDto updatedDog1Response = given()
                .contentType(ContentType.JSON)
                .body(updatedDog1,
                        ObjectMapperType.JACKSON_2)
                .put(HOST + "/dog/{id}", dog1.getId())
                .as(DogDto.class);

        Assert.assertEquals(updatedDog1, updatedDog1Response);

        DogDto getDog = given()
                .get(HOST + "/dog/{id}", dog1.getId())
                .as(DogDto.class);

        Assert.assertEquals(getDog, updatedDog1Response);
    }
}