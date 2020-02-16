package com.epam.javacource.sid.spring.controller;

import com.epam.javacource.sid.spring.model.DogDto;
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

public class DogDtoValidationTest {

    private Validator validator;

    @BeforeMethod
    public void setUp() {
        ValidatorFactory validatorFactory = Validation.buildDefaultValidatorFactory();
        validator = validatorFactory.getValidator();
    }

    @Test
    public void whenDogIsValidNoExceptionsProvided() {
        DogDto validDog = getValidDog();

        Set<ConstraintViolation<DogDto>> constraintViolations = validator.validate(validDog);

        Assert.assertTrue(constraintViolations.isEmpty());
    }

    @Test
    public void whenDogIdIsZeroExceptionsProvided() {
        DogDto validDog = getValidDog();
        validDog.setId(0);

        Set<ConstraintViolation<DogDto>> constraintViolations = validator.validate(validDog);

        Assert.assertFalse(constraintViolations.isEmpty());
    }

    @Test
    public void whenDogIdIsNegativeExceptionsProvided() {
        DogDto validDog = getValidDog();
        validDog.setId(-1);

        Set<ConstraintViolation<DogDto>> constraintViolations = validator.validate(validDog);

        Assert.assertFalse(constraintViolations.isEmpty());
    }

    @Test
    public void whenDogHeightIsZeroExceptionsProvided() {
        DogDto validDog = getValidDog();
        validDog.setHeight(0L);

        Set<ConstraintViolation<DogDto>> constraintViolations = validator.validate(validDog);

        Assert.assertFalse(constraintViolations.isEmpty());
    }

    @Test
    public void whenDogHeightIsNegativeExceptionsProvided() {
        DogDto validDog = getValidDog();
        validDog.setHeight(-1L);

        Set<ConstraintViolation<DogDto>> constraintViolations = validator.validate(validDog);

        Assert.assertFalse(constraintViolations.isEmpty());
    } 
    
    @Test
    public void whenDogNameIsTooLongExceptionsProvided() {
        DogDto validDog = getValidDog();
        String veryLongName = Strings.repeat("A", 101);
        validDog.setName(veryLongName);

        Set<ConstraintViolation<DogDto>> constraintViolations = validator.validate(validDog);

        Assert.assertFalse(constraintViolations.isEmpty());
    }

    @Test
    public void whenDogNameIsEmptyExceptionsProvided() {
        DogDto validDog = getValidDog();
        validDog.setName("");

        Set<ConstraintViolation<DogDto>> constraintViolations = validator.validate(validDog);

        Assert.assertFalse(constraintViolations.isEmpty());
    }

    @Test
    public void whenDogDOBInFutureExceptionsProvided() {
        DogDto validDog = getValidDog();
        validDog.setDateOfBirth(LocalDate.now().plusDays(1));

        Set<ConstraintViolation<DogDto>> constraintViolations = validator.validate(validDog);

        Assert.assertFalse(constraintViolations.isEmpty());
    }

    private DogDto getValidDog() {
        return new DogDto(null, "Dog1Name", LocalDate.now().minusDays(1), 1L, 1L);
    }
}
