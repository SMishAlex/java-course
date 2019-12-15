package com.epam.javacource.sid.webapp.task1;

import javax.servlet.ServletRequestEvent;
import javax.servlet.ServletRequestListener;
import java.util.Arrays;

public class CustomServletRequestListener implements ServletRequestListener {

    public void requestInitialized(ServletRequestEvent sre) {

        sre.getServletRequest().getParameterMap()
                .forEach((key, value) ->
                        System.out.printf(" param: %s\n" +
                                        "values: %s\n",
                                key, Arrays.toString(value)));
    }

    public void requestDestroyed(ServletRequestEvent sre) {
        //do nothing
    }
}
