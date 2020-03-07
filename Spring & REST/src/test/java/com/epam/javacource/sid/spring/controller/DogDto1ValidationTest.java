package com.epam.javacource.sid.spring.controller;

import com.epam.javacource.sid.spring.model.Dog;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import org.testng.util.Strings;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.time.LocalDate;
import java.util.Set;

public class DogDto1ValidationTest {

    private Validator validator;

    @BeforeMethod
    public void setUp() {
        ValidatorFactory validatorFactory = Validation.buildDefaultValidatorFactory();
        validator = validatorFactory.getValidator();
    }

    @Test
    public void whenDogIsValidNoExceptionsProvided() {
        Dog validDog = getValidDog();

        Set<ConstraintViolation<Dog>> constraintViolations = validator.validate(validDog);

        Assert.assertTrue(constraintViolations.isEmpty());
    }

    @Test
    public void whenDogIdIsZeroExceptionsProvided() {
        Dog validDog = getValidDog();
        validDog.setId(0);

        Set<ConstraintViolation<Dog>> constraintViolations = validator.validate(validDog);

        Assert.assertFalse(constraintViolations.isEmpty());
    }

    @Test
    public void whenDogIdIsNegativeExceptionsProvided() {
        Dog validDog = getValidDog();
        validDog.setId(-1);

        Set<ConstraintViolation<Dog>> constraintViolations = validator.validate(validDog);

        Assert.assertFalse(constraintViolations.isEmpty());
    }

    @Test
    public void whenDogHeightIsZeroExceptionsProvided() {
        Dog validDog = getValidDog();
        validDog.setHeight(0L);

        Set<ConstraintViolation<Dog>> constraintViolations = validator.validate(validDog);

        Assert.assertFalse(constraintViolations.isEmpty());
    }

    @Test
    public void whenDogHeightIsNegativeExceptionsProvided() {
        Dog validDog = getValidDog();
        validDog.setHeight(-1L);

        Set<ConstraintViolation<Dog>> constraintViolations = validator.validate(validDog);

        Assert.assertFalse(constraintViolations.isEmpty());
    } 
    
    @Test
    public void whenDogNameIsTooLongExceptionsProvided() {
        Dog validDog = getValidDog();
        String veryLongName = Strings.repeat("A", 101);
        validDog.setName(veryLongName);

        Set<ConstraintViolation<Dog>> constraintViolations = validator.validate(validDog);

        Assert.assertFalse(constraintViolations.isEmpty());
    }

    @Test
    public void whenDogNameIsEmptyExceptionsProvided() {
        Dog validDog = getValidDog();
        validDog.setName("");

        Set<ConstraintViolation<Dog>> constraintViolations = validator.validate(validDog);

        Assert.assertFalse(constraintViolations.isEmpty());
    }

    @Test
    public void whenDogDOBInFutureExceptionsProvided() {
        Dog validDog = getValidDog();
        validDog.setDateOfBirth(LocalDate.now().plusDays(1));

        Set<ConstraintViolation<Dog>> constraintViolations = validator.validate(validDog);

        Assert.assertFalse(constraintViolations.isEmpty());
    }

    private Dog getValidDog() {
        return new Dog(null, "Dog1Name", LocalDate.now().minusDays(1), 1L, 1L);
    }
}
