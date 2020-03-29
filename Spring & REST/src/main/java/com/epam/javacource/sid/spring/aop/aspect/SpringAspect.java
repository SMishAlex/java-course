package com.epam.javacource.sid.spring.aop.aspect;

import com.epam.javacource.sid.spring.dao.JdbcConnectionHolder;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class SpringAspect {

    private final JdbcConnectionHolder jdbcConnectionHolder;

    public void before() {
        jdbcConnectionHolder.startTransaction();
    }

    public void afterReturning() {
        jdbcConnectionHolder.commitTransaction();
    }

    public void afterThrowing() {
        jdbcConnectionHolder.rollbackTransaction();
    }
}
