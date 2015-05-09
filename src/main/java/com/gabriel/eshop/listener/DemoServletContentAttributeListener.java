package com.gabriel.eshop.listener;

import javax.servlet.ServletContextAttributeEvent;
import javax.servlet.ServletContextAttributeListener;
import java.util.Enumeration;

public class DemoServletContentAttributeListener implements ServletContextAttributeListener {

    public DemoServletContentAttributeListener() {
        System.out.println(">> MyServletContentAttributeListener - NEW");
    }

    @Override
    public void attributeAdded(ServletContextAttributeEvent servletContextAttributeEvent) {
        Enumeration names = servletContextAttributeEvent.getServletContext().getAttributeNames();
        while (names.hasMoreElements()) {
            System.out.println(">> ServletContext - attribute added " + names.nextElement());
        }
    }

    @Override
    public void attributeRemoved(ServletContextAttributeEvent servletContextAttributeEvent) {
        Enumeration names = servletContextAttributeEvent.getServletContext().getAttributeNames();
        while (names.hasMoreElements()) {
            System.out.println(">> ServletContext - attribute removed " + names.nextElement());
        }
    }

    @Override
    public void attributeReplaced(ServletContextAttributeEvent servletContextAttributeEvent) {
        Enumeration names = servletContextAttributeEvent.getServletContext().getAttributeNames();
        while (names.hasMoreElements()) {
            System.out.println(">> ServletContext - attribute replaced " + names.nextElement());
        }
    }
}
