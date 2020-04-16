package com.epam.javacource.sid.spring.controller;

import com.epam.javacource.sid.spring.dao.Dao;
import com.epam.javacource.sid.spring.exceptions.DatabaseCommunicationException;
import com.epam.javacource.sid.spring.model.Dog;
import org.apache.tomcat.jdbc.pool.DataSourceFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.springframework.test.context.web.WebAppConfiguration;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;
import org.testng.util.Strings;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;
import java.time.LocalDate;
import java.util.Properties;

@ContextConfiguration(locations = {"classpath*:spring-mvc-config.xml"})
@WebAppConfiguration
public class JdbcDaoTest extends AbstractTestNGSpringContextTests {

    @Autowired
    Dao<Dog> dogDao;

    @BeforeTest
    public static void setUpDataSource() throws Exception {
        try {
            System.setProperty(Context.INITIAL_CONTEXT_FACTORY, "org.apache.naming.java.javaURLContextFactory");
            System.setProperty(Context.URL_PKG_PREFIXES, "org.apache.naming");

            final DataSourceFactory dataSourceFactory = new DataSourceFactory();
            final Properties properties = new Properties();
            properties.put("name", "jdbc/DatabaseName");
            properties.put("auth", "Container");
            properties.put("type", "javax.sql.DataSource");
            properties.put("username", "dog");
            properties.put("password", "dog");
            properties.put("url", "jdbc:postgresql://localhost:5432/dog");
            properties.put("driverClassName", "org.postgresql.Driver");
            properties.put("initialSize", "20");
            properties.put("maxWaitMillis", "15000");
            properties.put("maxTotal", "75");
            properties.put("maxIdle", "20");
            properties.put("maxAge", "7200000");
            properties.put("testOnBorrow", "true");
            properties.put("validationQuery", "select 1");
            final DataSource dataSource = dataSourceFactory.createDataSource(properties);

            InitialContext ic = new InitialContext();

            ic.createSubcontext("java:comp");
            ic.createSubcontext("java:comp/env");
            ic.createSubcontext("java:comp/env/jdbc");
            ic.createSubcontext("java:comp/env/jdbc/LocalDatabaseName");

            ic.rebind("java:comp/env/jdbc/LocalDatabaseName", dataSource);
        } catch (NamingException ex) {
            ex.printStackTrace();
        }
    }

    @Test
    public void whenDogIsValidNoExceptionsProvided() {
        Dog validDog = getValidDog();

        dogDao.create(validDog);
    }

    @Test
    public void whenDogIdIsZeroExceptionsProvided() {
        Dog validDog = getValidDog();
        validDog.setId(0);

        dogDao.create(validDog);
    }

    @Test(expectedExceptions = {DatabaseCommunicationException.class})
    public void whenDogHeightIsZeroExceptionsProvided() {
        Dog validDog = getValidDog();
        validDog.setHeight(0L);

        dogDao.create(validDog);
    }

    @Test(expectedExceptions = {DatabaseCommunicationException.class})
    public void whenDogHeightIsNegativeExceptionsProvided() {
        Dog validDog = getValidDog();
        validDog.setHeight(-1L);

        dogDao.create(validDog);
    }

    @Test
    //@Test(expectedExceptions = {DatabaseCommunicationException.class})
    public void whenDogNameContainsSqlInjectionWeAreSad() {
        Dog validDog = getValidDog();
        String sqlInjectionName = "\"' blah";
        validDog.setName(sqlInjectionName);

        dogDao.create(validDog);
    }

    @Test
    public void whenDogNameIsLongButNotTooLongWeAreFine() {
        Dog validDog = getValidDog();
        String veryLongName = Strings.repeat("A", 100);
        validDog.setName(veryLongName);

        dogDao.create(validDog);
    }

    @Test(expectedExceptions = {DatabaseCommunicationException.class})
    public void whenDogNameIsTooLongExceptionsProvided() {
        Dog validDog = getValidDog();
        String veryLongName = Strings.repeat("A", 101);
        validDog.setName(veryLongName);

        dogDao.create(validDog);
    }

    @Test(expectedExceptions = {DatabaseCommunicationException.class})
    public void whenDogNameIsEmptyExceptionsProvided() {
        Dog validDog = getValidDog();
        validDog.setName("");

        dogDao.create(validDog);
    }

    private Dog getValidDog() {
        return new Dog(null, "Dog1Name", LocalDate.now().minusDays(1), 1L, 1L);
    }
}
