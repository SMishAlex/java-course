package com.epam.javacource.sid.webapp.task3;

import javax.servlet.ServletRequestEvent;
import javax.servlet.ServletRequestListener;
import java.util.Arrays;

public class CustomServletRequestListener implements ServletRequestListener {

    public void requestInitialized(ServletRequestEvent sre) {
        System.out.println(Thread.currentThread().getName());
    }

    public void requestDestroyed(ServletRequestEvent sre) {
        //do nothing
    }
}
