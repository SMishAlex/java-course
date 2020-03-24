package com.epam.javacource.sid.spring.service.proxy;

import com.epam.javacource.sid.spring.dao.JdbcConnectionHolder;
import net.sf.cglib.proxy.Enhancer;

import java.lang.reflect.Proxy;

public class ProxyFactory {

    @SuppressWarnings("unchecked")
    public static <T> T createDynamicProxy(JdbcConnectionHolder jdbcConnectionHolder, Object target,
                                           Class<? super T> targetInterface) {
        return (T) Proxy.newProxyInstance(TransactionalProxy.class.getClassLoader(),
                new Class[]{targetInterface},
                new TransactionalProxy(jdbcConnectionHolder, target));
    }

    @SuppressWarnings("unchecked")
    public static <T> T createCglibProxy(JdbcConnectionHolder jdbcConnectionHolder,
                                         Class<? super T> targetClass, Class<?>[] argumentTypes, Object[] arguments) {
        final Enhancer enhancer = new Enhancer();
        enhancer.setSuperclass(targetClass);
        final CglibTransactionalProxyInterceptor cglibTransactionalProxyInterceptor
                = new CglibTransactionalProxyInterceptor(jdbcConnectionHolder);
        enhancer.setCallback(cglibTransactionalProxyInterceptor);

        return (T) enhancer.create(argumentTypes, arguments);
    }
}
