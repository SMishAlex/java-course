package com.epam.javacource.sid.webapp.task3;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

public class CustomHttpServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        resp.setStatus(200);
//        System.out.println(Thread.activeCount());
        reallyHardWork();
        PrintWriter writer = resp.getWriter();
        writer.println("Hello servlet world!");
    }

    private void reallyHardWork() {
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
