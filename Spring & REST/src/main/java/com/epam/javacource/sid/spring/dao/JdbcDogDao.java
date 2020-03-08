package com.epam.javacource.sid.spring.dao;

import com.epam.javacource.sid.spring.exceptions.DatabaseCommunicationException;
import com.epam.javacource.sid.spring.exceptions.ResourceNotFoundException;
import com.epam.javacource.sid.spring.model.Dog;

import javax.sql.DataSource;
import java.sql.*;

public class JdbcDogDao implements Dao<Dog> {

    private final DataSource dataSource;

    public JdbcDogDao(DataSource ds) {
        dataSource = ds;
    }

    @Override
    public Dog create(Dog entity) {
        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement =
                     connection.prepareStatement("INSERT INTO DOGS (NAME, DATEOFBIRTH, HEIGHT, WEIGHT) "
                             + "VALUES ( ?, ?, ?, ? )", Statement.RETURN_GENERATED_KEYS)) {

            preparedStatement.setString(1, entity.getName());
            preparedStatement.setDate(2, Date.valueOf(entity.getDateOfBirth()));
            preparedStatement.setLong(3, entity.getHeight());
            preparedStatement.setLong(4, entity.getWeight());
            try {
                connection.setAutoCommit(false);
                preparedStatement.executeUpdate();

                try (ResultSet generatedKeys = preparedStatement.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        entity.setId(generatedKeys.getInt(1));
                    }
                }

                connection.commit();

            } catch (SQLException e) {
                connection.rollback();
                throw e;
            } finally {
                connection.setAutoCommit(true);
            }

            return entity;
        } catch (SQLException e) {
            e.printStackTrace();
            throw new DatabaseCommunicationException("Can't create your dog =(", e);
        }
    }

    @Override
    public Dog getOne(Integer id) {
        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement =
                     connection.prepareStatement(
                             "SELECT ID, NAME, DATEOFBIRTH, HEIGHT, WEIGHT FROM DOGS WHERE ID = ?")) {

            preparedStatement.setInt(1, id);
            try {
                connection.setAutoCommit(false);

                ResultSet resultSet = preparedStatement.executeQuery();

                connection.commit();
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
                connection.rollback();
                throw e;
            } finally {
                connection.setAutoCommit(true);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new DatabaseCommunicationException("Can't find your dog =(", e);
        }
    }


    @Override
    public Dog update(Dog entity) {
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

            try {
                connection.setAutoCommit(false);

                preparedStatement.executeUpdate();

                connection.commit();

            } catch (SQLException e) {
                connection.rollback();
                throw e;
            } finally {
                connection.setAutoCommit(true);
            }

            return entity;
        } catch (SQLException e) {
            e.printStackTrace();
            throw new DatabaseCommunicationException("Can't update your dog =(", e);
        }
    }

    @Override
    public void delete(Integer id) {
        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(
                     "DELETE FROM DOGS\n"
                             + "WHERE ID = ?")) {
            preparedStatement.setInt(1, id);
            try {
                connection.setAutoCommit(false);

                preparedStatement.executeUpdate();

                connection.commit();

            } catch (SQLException e) {
                connection.rollback();
                throw e;
            } finally {
                connection.setAutoCommit(true);
            }

        } catch (SQLException e) {
            e.printStackTrace();
            throw new DatabaseCommunicationException("Can't delete your dog =(", e);
        }
    }
}
