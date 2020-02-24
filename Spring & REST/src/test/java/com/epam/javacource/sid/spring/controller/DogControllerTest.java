package com.epam.javacource.sid.spring.controller;

import com.epam.javacource.sid.spring.model.DogDto;
import io.restassured.http.ContentType;
import io.restassured.mapper.ObjectMapperType;
import io.restassured.response.Response;
import io.restassured.response.ValidatableResponse;
import org.springframework.http.HttpStatus;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.time.LocalDate;

import static io.restassured.RestAssured.given;

public class DogControllerTest {

    private final static String HOST = "http://localhost:8888";

    private DogDto getDog1() {
        return new DogDto(null, "Dog1Name", LocalDate.now().minusDays(1), 10L, 10L);
    }

    private DogDto getUpdatedDog(Integer dogId) {
        return new DogDto(dogId, "Updated Dog1Name", LocalDate.now().minusDays(1), 15L, 15L);
    }


    @Test
    public void testCreatingDog() {
        ValidatableResponse response = given()
                .contentType(ContentType.JSON)
                .body(getDog1(), ObjectMapperType.JACKSON_2)
                .when()
                .post(HOST + "/dog")
                .then();

        System.out.println(response.extract().body().asString());
        response.statusCode(HttpStatus.OK.value());
    }

    @Test
    public void testGettingCreatedDogById() {
        DogDto dog1 = given()
                .contentType(ContentType.JSON)
                .body(getDog1(), ObjectMapperType.JACKSON_2)
                .post(HOST + "/dog")
                .as(DogDto.class, ObjectMapperType.JACKSON_2);

        DogDto getDog = given()
                .get(HOST + "/dog/{id}", dog1.getId())
                .as(DogDto.class);

        Assert.assertEquals(dog1, getDog);
    }

    @Test
    public void testUpdatingCreatedDogById() {
        Response postResponse = given()
                .contentType(ContentType.JSON)
                .body(getDog1(), ObjectMapperType.JACKSON_2)
                .post(HOST + "/dog");
        System.out.println(postResponse.asString());
        DogDto dog1 = postResponse
                .as(DogDto.class, ObjectMapperType.JACKSON_2);


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
        final ValidatableResponse response = given()
                .contentType(ContentType.JSON)
                .body(validDog,
                        ObjectMapperType.JACKSON_2)
                .when()
                .post(HOST + "/dog")
                .then();

        System.out.println(response.extract().body().asString());

        response
                .statusCode(HttpStatus.BAD_REQUEST.value());
    }
}