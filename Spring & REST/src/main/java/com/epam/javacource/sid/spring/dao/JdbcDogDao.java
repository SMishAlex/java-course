package com.epam.javacource.sid.spring.dao;

import com.epam.javacource.sid.spring.exceptions.DatabaseCommunicationException;
import com.epam.javacource.sid.spring.exceptions.ResourceNotFoundException;
import com.epam.javacource.sid.spring.model.Dog;

import java.sql.*;

public class JdbcDogDao implements Dao<Dog> {

    private JdbcConnectionHolder connectionHolder;

    public JdbcDogDao(JdbcConnectionHolder jdbcConnectionHolder) {
        connectionHolder = jdbcConnectionHolder;
    }

    @Override
    public Dog create(Dog entity) {
        try (Connection connection = connectionHolder.getConnection();
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
    public Dog getOne(Integer id) {
        try (Connection connection = connectionHolder.getConnection();
             PreparedStatement preparedStatement =
                     connection.prepareStatement(
                             "SELECT ID, NAME, DATEOFBIRTH, HEIGHT, WEIGHT FROM DOGS WHERE ID = ?")) {

            preparedStatement.setInt(1, id);

            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                return new Dog(
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
            throw new DatabaseCommunicationException("Can't find your dog =(", e);
        }
    }


    @Override
    public Dog update(Dog entity) {
        try (Connection connection = connectionHolder.getConnection();
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
        try (Connection connection = connectionHolder.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(
                     "DELETE FROM DOGS\n"
                             + "WHERE ID = ?")) {
            preparedStatement.setInt(1, id);

            preparedStatement.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
            throw new DatabaseCommunicationException("Can't delete your dog =(", e);
        }
    }
}
