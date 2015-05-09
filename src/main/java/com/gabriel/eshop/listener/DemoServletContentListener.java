package com.gabriel.eshop.listener;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

public class DemoServletContentListener implements ServletContextListener {
    public DemoServletContentListener() {
        System.out.println(">> MyServletContentListener - NEW");
    }

    @Override
    public void contextInitialized(ServletContextEvent servletContextEvent) {
        System.out.println(">> ServletContext - created, contextPath = " + servletContextEvent.getServletContext().getContextPath());
    }

    @Override
    public void contextDestroyed(ServletContextEvent servletContextEvent) {
        System.out.println(">> ServletContext - destroyed, contextPath = " + servletContextEvent.getServletContext().getContextPath());
    }
}
