package com.epam.javacource.sid.spring.controller;

import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.springframework.test.context.web.WebAppConfiguration;
import org.testng.annotations.Ignore;
import org.testng.annotations.Test;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.function.Function;

@Ignore("this test is really long just unignore if U want to try overload db cache")
@Test
@ContextConfiguration(locations = "classpath:spring-mvc-config.xml")
@WebAppConfiguration
public class PreparedStatmentBroadfourceTest extends AbstractTestNGSpringContextTests {

    @Autowired
    DataSource pgDataSource;

    @Test
    @SneakyThrows
    public void whenPreparedStatementCacheOverloadedOnBDSideSomethingHappens() {
        try (final Connection connection = pgDataSource.getConnection()) {
            Function<Integer, String> aliasQueryGenerator =
                    aliasNumber -> String.format("select id as id%d from dogs where id=?", aliasNumber);
            //first run just to init prepared statements
            for (int i = 0; i < Integer.MAX_VALUE; i++) {
                try (final PreparedStatement preparedStatement
                             = connection.prepareStatement(aliasQueryGenerator.apply(i))) {
                    preparedStatement.setInt(1, 1);

                    preparedStatement.executeQuery();
                }
            }

            //second run to find if they still correct
            for (int i = 0; i < Integer.MAX_VALUE; i++) {
                try (final PreparedStatement preparedStatement
                             = connection.prepareStatement(aliasQueryGenerator.apply(i))) {
                    preparedStatement.setInt(1, 1);

                    preparedStatement.executeQuery();
                }
            }
        }
    }
}
