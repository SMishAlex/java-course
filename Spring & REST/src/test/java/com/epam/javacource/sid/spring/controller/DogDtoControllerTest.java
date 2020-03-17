package com.epam.javacource.sid.spring.controller;

import com.epam.javacource.sid.spring.model.Dog;
import io.restassured.http.ContentType;
import io.restassured.mapper.ObjectMapperType;
import io.restassured.response.Response;
import io.restassured.response.ValidatableResponse;
import org.assertj.core.api.Assertions;
import org.springframework.http.HttpStatus;
import org.testng.annotations.Test;

import java.time.LocalDate;

import static io.restassured.RestAssured.given;

public class DogDtoControllerTest {

    private final static String HOST = "http://localhost:8081";

    private Dog getDog1() {
        return new Dog(null, "Dog1Name", LocalDate.now().minusDays(1), 10L, 10L);
    }

    private Dog getUpdatedDog(Integer dogId) {
        return new Dog(dogId, "Updated Dog1Name", LocalDate.now().minusDays(1), 15L, 15L);
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
        Dog dog = given()
                .contentType(ContentType.JSON)
                .body(getDog1(), ObjectMapperType.JACKSON_2)
                .post(HOST + "/dog")
                .as(Dog.class, ObjectMapperType.JACKSON_2);

        Dog getDog = given()
                .get(HOST + "/dog/{id}", dog.getId())
                .as(Dog.class);

        Assertions.assertThat(getDog)
                .usingRecursiveComparison()
                .isEqualTo(dog);
    }

    @Test
    public void testUpdatingCreatedDogById() {
        Response postResponse = given()
                .contentType(ContentType.JSON)
                .body(getDog1(), ObjectMapperType.JACKSON_2)
                .post(HOST + "/dog");
        System.out.println(postResponse.asString());
        Dog dog = postResponse
                .as(Dog.class, ObjectMapperType.JACKSON_2);


        Dog updatedDog = getUpdatedDog(dog.getId());
        Dog updatedDogResponse = given()
                .contentType(ContentType.JSON)
                .body(updatedDog, ObjectMapperType.JACKSON_2)
                .put(HOST + "/dog/{id}", dog.getId())
                .as(Dog.class);

        Assertions.assertThat(updatedDogResponse)
                .usingRecursiveComparison()
                .isEqualTo(updatedDog);

        Dog getDog = given()
                .get(HOST + "/dog/{id}", dog.getId())
                .as(Dog.class);

        Assertions.assertThat(getDog)
                .usingRecursiveComparison()
                .isEqualTo(updatedDogResponse);
    }

    @Test
    public void testNegativeCreatingDog() {
        Dog validDog = getDog1();
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