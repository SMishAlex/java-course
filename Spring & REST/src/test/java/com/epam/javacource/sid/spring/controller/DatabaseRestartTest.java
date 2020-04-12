package com.epam.javacource.sid.spring.controller;

import lombok.SneakyThrows;
import org.postgresql.util.PSQLException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.springframework.test.context.web.WebAppConfiguration;
import org.testng.annotations.Ignore;
import org.testng.annotations.Test;

import javax.sql.DataSource;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatCode;

/**
 * Tests are ignored due to manual database restart is required in the middle of the tests.
 * U can add breakpoint on sout lines and run tests to reproduce the results.
 */
@Ignore
@Test
@ContextConfiguration(locations = "classpath:spring-mvc-config.xml")
@WebAppConfiguration
public class DatabaseRestartTest extends AbstractTestNGSpringContextTests {

    @Autowired
    private DataSource dataSource;

    /**
     * Test fails even if database was restarted.
     * <p>
     * java.lang.AssertionError:
     * Expecting:
     * <true>
     * to be equal to:
     * <false>
     * but was not.
     */
    @Test
    @SneakyThrows
    public void whenDatabaseRestartsConnectionsAreStillFalsePositiveValid() {
        List<Connection> connections = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            final Connection connection = dataSource.getConnection();
            connections.add(connection);
        }
        for (Connection connection : connections) {
            assertThat(connection.isValid(1_0000_000)).isTrue();
        }
        // Database restart
        // #sudo service postgresql restart
        System.out.println("Database restart is expected");
        for (Connection connection : connections) {
            assertThat(connection.isValid(1_0000_000)).isFalse();
        }
    }

    /**
     * Test passed with exception:
     * "org.postgresql.util.PSQLException: ВАЖНО: закрытие подключения по команде администратора"
     * Sorry for russian in exception message =)
     */
    @Test
    @SneakyThrows
    public void whenExecutingQueryOnConnectionsAllocatedBeforeDBRestartExceptionThrown() {
        List<Connection> connections = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            final Connection connection = dataSource.getConnection();
            connections.add(connection);
        }
        for (Connection connection : connections) {
            assertThat(connection.isValid(1_0000_000)).isTrue();
        }
        // Database restart
        System.out.println("Database restart is expected");
        // #sudo service postgresql restart
        for (Connection connection : connections) {
            assertThatCode(() -> {
                try {
                    connection.createStatement().executeQuery("SELECT * FROM dogs");
                } catch (Exception e) {
                    e.printStackTrace();
                    throw e;
                }
            }).returns(PSQLException.class, Throwable::getClass);
        }
    }

    /**
     * Test passed.
     * As we can see c3p0 marks connections that throw an error as not valid and does not provide them to us again.
     */
    @Test
    @SneakyThrows
    public void whenExecutingQueryOnConnectionsAllocatedAfterDBRestartPoolCreatesNewValidConnections() {
        List<Connection> connections = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            final Connection connection = dataSource.getConnection();
            connections.add(connection);
        }
        for (Connection connection : connections) {
            assertThat(connection.isValid(1_0000_000)).isTrue();
        }
        // Database restart
        System.out.println("Database restart is expected");
        // #sudo service postgresql restart
        for (Connection connection : connections) {
            assertThatCode(() -> connection.createStatement().executeQuery("SELECT * FROM dogs"))
                    .returns(PSQLException.class, Throwable::getClass);
        }

        for (Connection connection : connections) {
            assertThat(connection.isValid(1_000_000)).isFalse();
            connection.close();
        }

        List<Connection> newConnections = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            final Connection connection = dataSource.getConnection();
            newConnections.add(connection);
        }
        for (Connection connection : newConnections) {
            assertThatCode(() -> connection.createStatement().executeQuery("SELECT * FROM dogs"))
                    .doesNotThrowAnyException();
        }
    }
}
