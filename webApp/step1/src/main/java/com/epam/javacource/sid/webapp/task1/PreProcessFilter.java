package com.epam.javacource.sid.webapp.task1;

import javax.servlet.*;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

public class PreProcessFilter implements Filter {

    public void init(FilterConfig filterConfig) {
    }

    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        System.out.println("I'm doing something before request");
        String notExpectedParameter = request.getParameter("notExpectedParameter");
        if (notExpectedParameter == null) {
            chain.doFilter(request, response);
        } else {
            ((HttpServletResponse) response).setStatus(400);
            PrintWriter writer = response.getWriter();
            writer.println("There is something wrong with your request!");
        }
    }
}
