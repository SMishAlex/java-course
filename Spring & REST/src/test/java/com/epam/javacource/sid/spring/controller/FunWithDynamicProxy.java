package com.epam.javacource.sid.spring.controller;

import org.assertj.core.api.Assertions;
import org.testng.annotations.Test;

import java.lang.reflect.Proxy;
import java.util.List;

public class FunWithDynamicProxy {

    @SuppressWarnings("unchecked")
    private final List<String> proxiedList = (List<String>) Proxy.newProxyInstance(FunWithDynamicProxy.class.getClassLoader(),
            new Class[]{List.class},
            (proxy, method, args) -> {
                if (method.getName().equals("get")) {
                    return String.valueOf(args[0]);
                } else {
                    throw new RuntimeException(String.format("Method %s unsupported", method.getName()));
                }
            });

    @Test
    public void whenGetFirstElementOneReturned() {

        Assertions.assertThat(proxiedList.get(1))
                .isEqualTo("1");
    }

    @Test
    public void whenNotGetMethodCalledExceptionThrows() {

        Assertions.assertThatThrownBy(() -> proxiedList.add("some"))
                .extracting(Throwable::getMessage)
                .isEqualTo("Method add unsupported");
    }
}
