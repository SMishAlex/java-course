package com.epam.javacource.sid.spring.controller;

import com.epam.javacource.sid.spring.dao.Dao;
import com.epam.javacource.sid.spring.exceptions.DatabaseCommunicationException;
import com.epam.javacource.sid.spring.model.DogDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.springframework.test.context.web.WebAppConfiguration;
import org.testng.annotations.Test;
import org.testng.util.Strings;

import java.time.LocalDate;

@Test
@ContextConfiguration(locations = "file:**/spring-mvc-config.xml")
@WebAppConfiguration
public class JdbcDaoTest extends AbstractTestNGSpringContextTests {

    @Autowired
    Dao<DogDto> dogDao;

    @Test
    public void whenDogIsValidNoExceptionsProvided() {
        DogDto validDog = getValidDog();

        dogDao.create(validDog);
    }

    @Test
    public void whenDogIdIsZeroExceptionsProvided() {
        DogDto validDog = getValidDog();
        validDog.setId(0);

        dogDao.create(validDog);
    }

    @Test(expectedExceptions = {DatabaseCommunicationException.class})
    public void whenDogHeightIsZeroExceptionsProvided() {
        DogDto validDog = getValidDog();
        validDog.setHeight(0L);

        dogDao.create(validDog);
    }

    @Test(expectedExceptions = {DatabaseCommunicationException.class})
    public void whenDogHeightIsNegativeExceptionsProvided() {
        DogDto validDog = getValidDog();
        validDog.setHeight(-1L);

        dogDao.create(validDog);
    }

    @Test
    //@Test(expectedExceptions = {DatabaseCommunicationException.class})
    public void whenDogNameContainsSqlInjectionWeAreSad() {
        DogDto validDog = getValidDog();
        String sqlInjectionName = "\"' blah";
        validDog.setName(sqlInjectionName);

        dogDao.create(validDog);
    }

    @Test
    public void whenDogNameIsLongButNotTooLongWeAreFine() {
        DogDto validDog = getValidDog();
        String veryLongName = Strings.repeat("A", 100);
        validDog.setName(veryLongName);

        dogDao.create(validDog);
    }

    @Test(expectedExceptions = {DatabaseCommunicationException.class})
    public void whenDogNameIsTooLongExceptionsProvided() {
        DogDto validDog = getValidDog();
        String veryLongName = Strings.repeat("A", 101);
        validDog.setName(veryLongName);

        dogDao.create(validDog);
    }

    @Test(expectedExceptions = {DatabaseCommunicationException.class})
    public void whenDogNameIsEmptyExceptionsProvided() {
        DogDto validDog = getValidDog();
        validDog.setName("");

        dogDao.create(validDog);
    }

    private DogDto getValidDog() {
        return new DogDto(null, "Dog1Name", LocalDate.now().minusDays(1), 1L, 1L);
    }
}