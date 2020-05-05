package com.epam.javacource.sid.spring.controller;

import lombok.*;
import org.hibernate.FlushMode;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.engine.spi.SharedSessionContractImplementor;
import org.hibernate.id.IdentifierGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Commit;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTransactionalTestNGSpringContextTests;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Commit
@ContextConfiguration(locations = {"classpath*:spring-mvc-config.xml"})
public class HibernateFlashModeTest extends AbstractTransactionalTestNGSpringContextTests {

    @Autowired
    SessionFactory sessionFactory;

    @DataProvider(name = "flashModesAndEntities", parallel = false)
    public static Object[][] dataProvider() {
        final List<Class<? extends AbstractDog>> dogClasses =
                Arrays.asList(DogWithAutoId.class, DogWithIdentityId.class, DogWithSequenceId.class);
        final List<Object[]> objects = new ArrayList<>();
        for (Class<? extends AbstractDog> dogClass : dogClasses) {
            for (FlushMode flushMode : FlushMode.values()) {
                objects.add(new Object[]{flushMode, dogClass, true});
                objects.add(new Object[]{flushMode, dogClass, false});
            }
        }
        return objects.toArray(new Object[0][]);
    }

    @SneakyThrows
    @Test(dataProvider = "flashModesAndEntities", singleThreaded = true)
    public void whenFlashMode(FlushMode flushMode, Class<? extends AbstractDog> dogClass, boolean flushDirectly) {

        System.out.printf("flushMode=%s, dogClass=%s, flushDirectly=%s\n", flushMode, dogClass, flushDirectly);
        Session session = sessionFactory.getCurrentSession();
        session.setHibernateFlushMode(flushMode);

        AbstractDog dog = dogClass.newInstance();
        dog.setName("DogForTest");
        dog.setHeight(100L);
        dog.setWeight(100L);
        System.out.println(dog);
        System.out.println("Calling save method");
        session.save(dog);
        System.out.println(dog);
        if (flushDirectly) {
            System.out.println("Calling flush method");
            session.flush();
        }
        System.out.println("Calling select");
        //flushMode=ALWAYS
        //Hibernate: insert into dogs (dateOfBirth, height, name, weight, id) values (?, ?, ?, ?, ?)
        //Hibernate: SELECT * FROM dogs
        final List list = session.createNativeQuery("SELECT * FROM dogs").list();

        System.out.println("Transaction ends");
    }


    @AllArgsConstructor
    @NoArgsConstructor
    @Getter
    @Setter
    @ToString
    @MappedSuperclass
    public static class AbstractDog {
        private String name;
        private LocalDate dateOfBirth;
        private Long height;
        private Long weight;
    }


    @Entity
    @Table(name = "DOGS")
    @AllArgsConstructor
    @NoArgsConstructor
    @Getter
    @Setter
    @ToString(callSuper = true)
    public static class DogWithAutoId extends AbstractDog {
        @Id
        @GeneratedValue(strategy = GenerationType.AUTO)
        private Integer id;
    }

    @Entity
    @Table(name = "DOGS")
    @AllArgsConstructor
    @NoArgsConstructor
    @Getter
    @Setter
    @ToString(callSuper = true)
    public static class DogWithIdentityId extends AbstractDog {
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Integer id;
    }

    @Entity(name = "DogWithSequenceId")
    @Table(name = "DOGS")
    @AllArgsConstructor
    @NoArgsConstructor
    @Getter
    @Setter
    @ToString(callSuper = true)
    public static class DogWithSequenceId extends AbstractDog {
        @Id
        @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "dogSequence")
        @GenericGenerator(name = "dogSequence",
                strategy = "com.epam.javacource.sid.spring.controller.HibernateFlashModeTest$DogWithSequenceSequenceGenerator")
        private Integer id;
    }

    public static class DogWithSequenceSequenceGenerator implements IdentifierGenerator {
        @Override
        public Serializable generate(SharedSessionContractImplementor session, Object object) throws HibernateException {

            int max = session.createQuery("SELECT MAX(d.id) FROM DogWithSequenceId d", Integer.class)
                    .getSingleResult();

            return max + 1;
        }
    }
}
