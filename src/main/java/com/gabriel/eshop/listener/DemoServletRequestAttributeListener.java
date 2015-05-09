package com.gabriel.eshop.listener;

import javax.servlet.ServletRequestAttributeEvent;
import javax.servlet.ServletRequestAttributeListener;
import java.util.Enumeration;

public class DemoServletRequestAttributeListener implements ServletRequestAttributeListener {

    public DemoServletRequestAttributeListener() {
        System.out.println(">> MyServletRequestAttributeListener - NEW");
    }

    @Override
    public void attributeAdded(ServletRequestAttributeEvent servletRequestAttributeEvent) {
        Enumeration names = servletRequestAttributeEvent.getServletRequest().getAttributeNames();
        while (names.hasMoreElements()) {
            System.out.println(">> ServletRequest - attribute added " + names.nextElement());
        }
    }

    @Override
    public void attributeRemoved(ServletRequestAttributeEvent servletRequestAttributeEvent) {
        Enumeration names = servletRequestAttributeEvent.getServletRequest().getAttributeNames();
        while (names.hasMoreElements()) {
            System.out.println(">> ServletRequest - attribute removed " + names.nextElement());
        }
    }

    @Override
    public void attributeReplaced(ServletRequestAttributeEvent servletRequestAttributeEvent) {
        Enumeration names = servletRequestAttributeEvent.getServletRequest().getAttributeNames();
        while (names.hasMoreElements()) {
            System.out.println(">> ServletRequest - attribute replaced " + names.nextElement());
        }
    }
}
