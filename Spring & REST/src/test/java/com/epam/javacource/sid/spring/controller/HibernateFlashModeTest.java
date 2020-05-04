package com.epam.javacource.sid.spring.controller;

import lombok.*;
import org.hibernate.FlushMode;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;
import org.springframework.test.annotation.Commit;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTransactionalTestNGSpringContextTests;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import javax.persistence.*;
import java.time.LocalDate;

@Commit
@ContextConfiguration(locations = {"classpath*:spring-mvc-config.xml"})
public class HibernateFlashModeTest extends AbstractTransactionalTestNGSpringContextTests {

    @Autowired
    SessionFactory sessionFactory;

    @DataProvider(name = "flashModesAndEntities")
    public static Object[][] dataProvider() {
        return new Object[][]{
                {FlushMode.ALWAYS, DogWithAutoId.class}
        };
    }

    @SneakyThrows
    @Test(dataProvider = "flashModesAndEntities")
    public void whenFlashMode(FlushMode flushMode, Class<? extends DogInterface> dogClass) {

        Session session = sessionFactory.getCurrentSession();
        session.setHibernateFlushMode(flushMode);

        DogInterface dog = dogClass.newInstance();
        System.out.println("Before session.save call");
        System.out.println("After session.save call");
        dog.setName("DogForTest");
        dog.setHeight(100L);
        dog.setWeight(100L);
        System.out.println("After dog.setName call");
        System.out.println(dog);
        session.save(dog);
        System.out.println(dog);
        session.flush();

    }

    @Entity
    @Table(name = "DOGS")
    @AllArgsConstructor
    @NoArgsConstructor
    @Getter
    @Setter
    @ToString
    public static class DogWithAutoId implements DogInterface {
        @Id
        @GeneratedValue(strategy = GenerationType.AUTO)
        private Integer id;
        @Column(name = "NAME", nullable = false)
        private String name;
        private LocalDate dateOfBirth;
        private Long height;
        private Long weight;
    }
}
