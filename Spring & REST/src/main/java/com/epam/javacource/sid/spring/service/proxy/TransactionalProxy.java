package com.epam.javacource.sid.spring.service.proxy;

import com.epam.javacource.sid.spring.aop.annotations.IDontUseTryWithResourcesPleaseLetMeBeTransactional;
import com.epam.javacource.sid.spring.dao.JdbcConnectionHolder;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.Arrays;
import java.util.stream.Collectors;

public class TransactionalProxy implements InvocationHandler {

    private final JdbcConnectionHolder jdbcConnectionHolder;
    private final Object target;

    public TransactionalProxy(JdbcConnectionHolder jdbcConnectionHolder, Object target) {
        this.jdbcConnectionHolder = jdbcConnectionHolder;
        this.target = target;
    }

    @SuppressWarnings("unchecked")
    public static <T> T createProxy(JdbcConnectionHolder jdbcConnectionHolder, Object target,
                                    Class<? super T> targetInterface) {
        return (T) Proxy.newProxyInstance(TransactionalProxy.class.getClassLoader(),
                new Class[]{targetInterface},
                new TransactionalProxy(jdbcConnectionHolder, target));
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        final Method targetMethod = target.getClass().getDeclaredMethod(method.getName(),
                Arrays.stream(args).map(Object::getClass).toArray(Class[]::new));

        final IDontUseTryWithResourcesPleaseLetMeBeTransactional annotation
                = targetMethod.getAnnotation(IDontUseTryWithResourcesPleaseLetMeBeTransactional.class);

        if (annotation == null) {
            return method.invoke(target, args);
        }
        jdbcConnectionHolder.startTransaction();
        try {
            final Object result = method.invoke(target, args);
            jdbcConnectionHolder.commitTransaction();
            return result;
        } catch (Exception e) {
            jdbcConnectionHolder.rollbackTransaction();
            throw e;
        } finally {
            jdbcConnectionHolder.closeConnection();
        }
    }
}
