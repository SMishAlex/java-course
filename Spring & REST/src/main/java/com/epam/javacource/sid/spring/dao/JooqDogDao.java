package com.epam.javacource.sid.spring.dao;

import com.epam.javacource.sid.spring.exceptions.ResourceNotFoundException;
import com.epam.javacource.sid.spring.model.Dog;
import org.jooq.DSLContext;
import org.jooq.SQLDialect;
import org.jooq.impl.DSL;
import org.jooq.impl.DataSourceConnectionProvider;
import org.jooq.impl.DefaultConfiguration;

import javax.sql.DataSource;

import static com.epam.javacource.sid.generated.jooq.public_.tables.Dogs.DOGS;

public class JooqDogDao implements Dao<Dog> {

    private final DSLContext dsl;

    public JooqDogDao(DataSource dataSource, SQLDialect dialect) {
        DefaultConfiguration jooqConfiguration = new DefaultConfiguration();
        jooqConfiguration.set(new DataSourceConnectionProvider(dataSource));
        jooqConfiguration.set(dialect);
        dsl = DSL.using(jooqConfiguration);
    }

    @Override
    public Dog create(Dog entity) {
        throw new UnsupportedOperationException("JooqDogDao is only for prepared statement testing " +
                "and only read operation implemented. Use other Dao implementations");
    }

    @Override
    public Dog getOne(Integer id) {
        return dsl.select()
                .from(DOGS)
                .where(DOGS.ID.eq(id))
                .fetchOne(r -> {
                    if (r == null) {
                        throw new ResourceNotFoundException("Seems like your dog is gone.");
                    }
                    Dog dog = new Dog();
                    dog.setId(r.get(DOGS.ID));
                    dog.setName(r.get(DOGS.NAME));
                    dog.setDateOfBirth(r.get(DOGS.DATEOFBIRTH));
                    dog.setHeight(r.get(DOGS.HEIGHT).longValue());
                    dog.setWeight(r.get(DOGS.WEIGHT).longValue());
                    return dog;
                });
    }

    @Override
    public Dog update(Dog entity) {
        throw new UnsupportedOperationException("JooqDogDao is only for prepared statement testing " +
                "and only read operation implemented. Use other Dao implementations");
    }

    @Override
    public void delete(Integer id) {
        throw new UnsupportedOperationException("JooqDogDao is only for prepared statement testing " +
                "and only read operation implemented. Use other Dao implementations");
    }
}
