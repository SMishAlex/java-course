package com.epam.javacource.sid.spring.service.proxy;

import com.epam.javacource.sid.spring.dao.JdbcConnectionHolder;
import net.sf.cglib.proxy.Enhancer;

import java.lang.reflect.Constructor;
import java.lang.reflect.Proxy;
import java.util.stream.Stream;

public class ProxyFactory {

    @SuppressWarnings("unchecked")
    public static <T, R extends T> T createDynamicProxy(JdbcConnectionHolder jdbcConnectionHolder, R target, Class<T> proxyType) {
        return (T) Proxy.newProxyInstance(TransactionalProxy.class.getClassLoader(),
                new Class[]{proxyType},
                new TransactionalProxy(jdbcConnectionHolder, target));
    }

    @SuppressWarnings("unchecked")
    public static <T> T createCglibProxy(JdbcConnectionHolder jdbcConnectionHolder, T target) {
        final Enhancer enhancer = new Enhancer();
        enhancer.setSuperclass(target.getClass());
        enhancer.setCallback(new CglibTransactionalProxyInterceptor(jdbcConnectionHolder, target));

        Constructor<?> constructor = Stream.of(target.getClass().getConstructors())
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Could not create proxy: no available constructor"));
        Class<?>[] parameterTypes = constructor.getParameterTypes();
        Object[] fakeArgs = new Object[parameterTypes.length];
        return (T) enhancer.create(parameterTypes, fakeArgs);
    }
}
