package com.epam.javacource.sid.spring.dao;

import com.epam.javacource.sid.spring.exceptions.DatabaseCommunicationException;
import com.epam.javacource.sid.spring.exceptions.ResourceNotFoundException;
import com.epam.javacource.sid.spring.model.Dog;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.Optional;

public class JdbcDogDao implements Dao<Dog> {

    private final JdbcTemplate jdbcTemplate;

    public JdbcDogDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Dog create(Dog entity) {
        try {
            final GeneratedKeyHolder keyHolder = new GeneratedKeyHolder();
            jdbcTemplate.update(connection -> {
                PreparedStatement preparedStatement = connection
                        .prepareStatement("INSERT INTO DOGS (NAME, DATEOFBIRTH, HEIGHT, WEIGHT) "
                                + "VALUES ( ?, ?, ?, ? )", Statement.RETURN_GENERATED_KEYS);

                preparedStatement.setString(1, entity.getName());
                preparedStatement.setDate(2, Date.valueOf(entity.getDateOfBirth()));
                preparedStatement.setLong(3, entity.getHeight());
                preparedStatement.setLong(4, entity.getWeight());

                return preparedStatement;
            }, keyHolder);

            final Object id = Optional.of(keyHolder)
                    .map(GeneratedKeyHolder::getKeys)
                    .map(generatedKeys -> generatedKeys.get("id"))
                    .orElseThrow(() -> new DatabaseCommunicationException("Can't create your dog =(", null));

            entity.setId((int) id);
            return entity;
        } catch (DataAccessException e) {
            e.printStackTrace();
            throw new DatabaseCommunicationException("Can't create your dog =(", e);
        }
    }

    @Override
    public Dog getOne(Integer id) {
        final Dog dog = jdbcTemplate.queryForObject("SELECT ID, NAME, DATEOFBIRTH, HEIGHT, WEIGHT FROM DOGS WHERE ID = ?",
                new Object[]{id},
                (rs, rowNum) -> new Dog(
                        rs.getInt("ID"),
                        rs.getString("NAME"),
                        rs.getDate("DATEOFBIRTH").toLocalDate(),
                        rs.getLong("HEIGHT"),
                        rs.getLong("WEIGHT")));
        if (dog == null) {
            throw new ResourceNotFoundException("Seems like your dog is gone.");
        } else {
            return dog;
        }
    }


    @Override
    public Dog update(Dog entity) {
        try {
            final int update = jdbcTemplate.update("UPDATE DOGS\n"
                            + "SET\n"
                            + "    NAME = ?,\n"
                            + "    DATEOFBIRTH = ?,\n"
                            + "    HEIGHT = ?,\n"
                            + "    WEIGHT = ?\n"
                            + "WHERE ID = ?",
                    entity.getName(),
                    Date.valueOf(entity.getDateOfBirth()),
                    entity.getHeight(),
                    entity.getWeight(),
                    entity.getId());
            if (update == 0) {
                throw new DatabaseCommunicationException("Can't update your dog =(", null);
            }
            return entity;
        } catch (DataAccessException e) {
            e.printStackTrace();
            throw new DatabaseCommunicationException("Can't update your dog =(", e);
        }
    }

    @Override
    public void delete(Integer id) {
        final int update = jdbcTemplate.update("DELETE FROM DOGS\n"
                + "WHERE ID = ?", id);
        if (update == 0) {
            throw new DatabaseCommunicationException("Can't delete your dog =(", null);
        }
    }
}
