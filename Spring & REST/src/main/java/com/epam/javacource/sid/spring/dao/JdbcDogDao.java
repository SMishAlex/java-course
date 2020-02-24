package com.epam.javacource.sid.spring.dao;

import com.epam.javacource.sid.spring.exceptions.DatabaseCommunicationException;
import com.epam.javacource.sid.spring.exceptions.ResourceNotFoundException;
import com.epam.javacource.sid.spring.model.DogDto;
import org.h2.jdbcx.JdbcDataSource;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class JdbcDogDao implements Dao<DogDto> {

    private final DataSource dataSource;

    public JdbcDogDao(JdbcDataSource ds) {
        dataSource = ds;
        try (Connection connection = dataSource.getConnection();
             Statement statement = connection.createStatement()) {
            statement.executeUpdate("CREATE TABLE IF NOT EXISTS DOGS("
                    + "ID integer AUTO_INCREMENT PRIMARY KEY,"
                    + "name VARCHAR(100) NOT NULL CHECK (length(name) > 0),"
                    + "dateOfBirth DATE,"
                    + "height INTEGER NOT NULL CHECK (height > 0),"
                    + "weight INTEGER NOT NULL CHECK (weight > 0))");
        } catch (SQLException e) {
            throw new DatabaseCommunicationException("Can't initiate database", e);
        }
    }

    @Override
    public DogDto create(DogDto entity) {
        try (Connection connection = dataSource.getConnection();
             Statement statement = connection.createStatement()) {
            statement.executeUpdate(String.format(
                    "INSERT INTO DOGS (NAME, DATEOFBIRTH, HEIGHT, WEIGHT) VALUES ( '%s', '%s', %s, %s )",
                    entity.getName(),
                    entity.getDateOfBirth(),
                    entity.getHeight(),
                    entity.getWeight()),
                    Statement.RETURN_GENERATED_KEYS);

            try (ResultSet generatedKeys = statement.getGeneratedKeys()) {

                if (generatedKeys.next()) {
                    entity.setId(generatedKeys.getInt(1));
                }
            }
            return entity;
        } catch (SQLException e) {
            throw new DatabaseCommunicationException("Can't create your dog =(", e);
        }
    }

    @Override
    public DogDto getOne(Integer id) {
        try (Connection connection = dataSource.getConnection();
             Statement statement = connection.createStatement()) {
            final ResultSet resultSet = statement.executeQuery(String.format(
                    "SELECT ID, NAME, DATEOFBIRTH, HEIGHT, WEIGHT FROM DOGS WHERE ID = %d",
                    id));

            if (resultSet.next()) {
                return new DogDto(
                        resultSet.getInt("ID"),
                        resultSet.getString("NAME"),
                        resultSet.getDate("DATEOFBIRTH").toLocalDate(),
                        resultSet.getLong("HEIGHT"),
                        resultSet.getLong("WEIGHT"));
            } else {
                throw new ResourceNotFoundException("Seems like your dog is gone.");
            }
        } catch (SQLException e) {
            throw new DatabaseCommunicationException("Can't create your dog =(", e);
        }
    }


    @Override
    public DogDto update(DogDto entity) {
        try (Connection connection = dataSource.getConnection();
             Statement statement = connection.createStatement()) {
            statement.executeUpdate(String.format(
                    "UPDATE DOGS\n"
                            + "SET\n"
                            + "    NAME = '%s',\n"
                            + "    DATEOFBIRTH = '%s',\n"
                            + "    HEIGHT = %s,\n"
                            + "    WEIGHT = %s\n"
                            + "WHERE ID = %s",
                    entity.getName(),
                    entity.getDateOfBirth(),
                    entity.getHeight(),
                    entity.getWeight(),
                    entity.getId()));

            return entity;
        } catch (SQLException e) {
            throw new DatabaseCommunicationException("Can't update your dog =(", e);
        }
    }

    @Override
    public void delete(Integer id) {
        try (Connection connection = dataSource.getConnection();
             Statement statement = connection.createStatement()) {
            statement.executeUpdate(String.format(
                    "DELETE FROM DOGS\n"
                            + "WHERE ID = %s",
                    id));
        } catch (SQLException e) {
            throw new DatabaseCommunicationException("Can't delete your dog =(", e);
        }
    }
}
