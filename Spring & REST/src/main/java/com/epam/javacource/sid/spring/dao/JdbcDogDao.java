package com.epam.javacource.sid.spring.dao;

import com.epam.javacource.sid.spring.exceptions.DatabaseCommunicationException;
import com.epam.javacource.sid.spring.exceptions.ResourceNotFoundException;
import com.epam.javacource.sid.spring.model.DogDto;

import javax.sql.DataSource;
import java.sql.*;

public class JdbcDogDao implements Dao<DogDto> {

    private final DataSource dataSource;

    public JdbcDogDao(DataSource ds) {
        dataSource = ds;
    }

    public void testPS() {
        try (Connection connection = dataSource.getConnection()) {
            try (PreparedStatement preparedStatement =
                         connection.prepareStatement("SELECT * FROM DOGS where ID = ?")) {

                preparedStatement.setInt(1, 1);
                preparedStatement.execute();
            }
            try (PreparedStatement preparedStatement =
                         connection.prepareStatement("SELECT * FROM DOGS where ID = ?")) {
                preparedStatement.setInt(1, 1);
                preparedStatement.execute();
            }
        } catch (SQLException e) {
            throw new DatabaseCommunicationException("Can't initiate database", e);
        }
    }

    public void initDB() {
        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement =
                     connection.prepareStatement("CREATE TABLE IF NOT EXISTS DOGS("
                             + "ID SERIAL PRIMARY KEY,"
                             + "name VARCHAR(100) NOT NULL CHECK (length(name) > 0),"
                             + "dateOfBirth DATE,"
                             + "height INTEGER NOT NULL CHECK (height > 0),"
                             + "weight INTEGER NOT NULL CHECK (weight > 0))")) {
            preparedStatement.execute();
        } catch (SQLException e) {
            throw new DatabaseCommunicationException("Can't initiate database", e);
        }
    }

    @Override
    public DogDto create(DogDto entity) {
        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement =
                     connection.prepareStatement("INSERT INTO DOGS (NAME, DATEOFBIRTH, HEIGHT, WEIGHT) "
                             + "VALUES ( ?, ?, ?, ? )", Statement.RETURN_GENERATED_KEYS)) {

            preparedStatement.setString(1, entity.getName());
            preparedStatement.setDate(2, Date.valueOf(entity.getDateOfBirth()));
            preparedStatement.setLong(3, entity.getHeight());
            preparedStatement.setLong(4, entity.getWeight());
            preparedStatement.executeUpdate();

            try (ResultSet generatedKeys = preparedStatement.getGeneratedKeys()) {

                if (generatedKeys.next()) {
                    entity.setId(generatedKeys.getInt(1));
                }
            }
            return entity;
        } catch (SQLException e) {
            e.printStackTrace();
            throw new DatabaseCommunicationException("Can't create your dog =(", e);
        }
    }

    @Override
    public DogDto getOne(Integer id) {
        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement =
                     connection.prepareStatement(
                             "SELECT ID, NAME, DATEOFBIRTH, HEIGHT, WEIGHT FROM DOGS WHERE ID = ?")) {

            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();

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
            e.printStackTrace();
            throw new DatabaseCommunicationException("Can't create your dog =(", e);
        }
    }


    @Override
    public DogDto update(DogDto entity) {
        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement("UPDATE DOGS\n"
                     + "SET\n"
                     + "    NAME = ?,\n"
                     + "    DATEOFBIRTH = ?,\n"
                     + "    HEIGHT = ?,\n"
                     + "    WEIGHT = ?\n"
                     + "WHERE ID = ?")) {

            preparedStatement.setString(1, entity.getName());
            preparedStatement.setDate(2, Date.valueOf(entity.getDateOfBirth()));
            preparedStatement.setLong(3, entity.getHeight());
            preparedStatement.setLong(4, entity.getWeight());
            preparedStatement.setInt(5, entity.getId());

            preparedStatement.executeUpdate();

            return entity;
        } catch (SQLException e) {
            e.printStackTrace();
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
            e.printStackTrace();
            throw new DatabaseCommunicationException("Can't delete your dog =(", e);
        }
    }
}
