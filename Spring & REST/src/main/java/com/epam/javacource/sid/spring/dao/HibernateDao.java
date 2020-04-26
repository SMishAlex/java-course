package com.epam.javacource.sid.spring.dao;

import com.epam.javacource.sid.spring.exceptions.DatabaseCommunicationException;
import com.epam.javacource.sid.spring.exceptions.ResourceNotFoundException;
import com.epam.javacource.sid.spring.model.Dog;
import org.hibernate.SessionFactory;
import org.springframework.dao.DataAccessException;

import java.io.Serializable;

public class HibernateDao implements Dao<Dog> {

    private final SessionFactory sessionFactory;

    public HibernateDao(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public Dog create(Dog entity) {
        try {
            final Serializable generatedId = sessionFactory.getCurrentSession().save(entity);
            return sessionFactory.getCurrentSession().get(Dog.class, generatedId);
        } catch (Exception e) {
            e.printStackTrace();
            throw new DatabaseCommunicationException("Can't create your dog =(", e);
        }
    }

    @Override
    public Dog getOne(Integer id) {
        try {
            return sessionFactory.getCurrentSession().get(Dog.class, id);
        } catch (Exception e) {
            throw new ResourceNotFoundException("Seems like your dog is gone.");
        }
    }


    @Override
    public Dog update(Dog entity) {
        try {
            sessionFactory.getCurrentSession().update(entity);
            return sessionFactory.getCurrentSession().get(Dog.class, entity.getId());
        } catch (DataAccessException e) {
            e.printStackTrace();
            throw new DatabaseCommunicationException("Can't update your dog =(", e);
        }
    }

    @Override
    public void delete(Integer id) {
        try {
            final Dog dogRef = sessionFactory.getCurrentSession().load(Dog.class, id);
            sessionFactory.getCurrentSession().delete(dogRef);
        } catch (Exception e) {
            throw new DatabaseCommunicationException("Can't delete your dog =(", null);

        }
    }
}
