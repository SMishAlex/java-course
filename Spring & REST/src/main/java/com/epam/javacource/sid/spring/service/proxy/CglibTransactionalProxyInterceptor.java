package com.epam.javacource.sid.spring.service.proxy;

import com.epam.javacource.sid.spring.aop.annotations.IDontUseTryWithResourcesPleaseLetMeBeTransactional;
import com.epam.javacource.sid.spring.dao.JdbcConnectionHolder;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;

public class CglibTransactionalProxyInterceptor implements MethodInterceptor {

    private final JdbcConnectionHolder jdbcConnectionHolder;

    public CglibTransactionalProxyInterceptor(JdbcConnectionHolder jdbcConnectionHolder) {
        this.jdbcConnectionHolder = jdbcConnectionHolder;
    }

    /**
     * See {@link MethodInterceptor#intercept}.
     */
    public Object intercept(Object obj, java.lang.reflect.Method method, Object[] args,
                            MethodProxy proxy) throws Throwable {
        if (method.isAnnotationPresent(IDontUseTryWithResourcesPleaseLetMeBeTransactional.class)) {
            jdbcConnectionHolder.startTransaction();
            try {
                final Object result = proxy.invokeSuper(obj, args);
                jdbcConnectionHolder.commitTransaction();
                return result;
            } catch (Exception e) {
                jdbcConnectionHolder.rollbackTransaction();
                throw e;
            } finally {
                jdbcConnectionHolder.closeConnection();
            }
        } else {
            return proxy.invokeSuper(obj, args);
        }
    }
}
