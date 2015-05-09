package com.gabriel.eshop.filter;

import javax.servlet.*;
import java.io.IOException;

public class ProcessingTimeFilter implements Filter {

    public ProcessingTimeFilter() {
        System.out.println(">> ProcessingTimeFilter - NEW");
    }

    public void destroy() {
        System.out.println(">> ProcessingTimeFilter - destroy");
    }

    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws ServletException, IOException {
        long startTime = System.nanoTime();
        chain.doFilter(request, response);
        long finishTime = System.nanoTime();
        System.out.println(">> ProcessingTimeFilter: dT = " + (finishTime - startTime));
        System.out.println();
    }

    public void init(FilterConfig config) throws ServletException {
        System.out.println(">> ProcessingTimeFilter - init");
    }

}
