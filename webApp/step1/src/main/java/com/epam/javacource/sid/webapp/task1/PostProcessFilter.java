package com.epam.javacource.sid.webapp.task1;

import javax.servlet.*;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

public class PostProcessFilter implements Filter {

    public void init(FilterConfig filterConfig) {
    }

    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {

        chain.doFilter(request, response);
        System.out.println("I'm doing something after processing");
        if (((HttpServletResponse) response).getStatus() == 200) {
            PrintWriter writer = response.getWriter();
            writer.println("This line is from filter btw...");
            writer.close();
        }
    }
}
