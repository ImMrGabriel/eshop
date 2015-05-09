package com.gabriel.eshop.listener;

import javax.servlet.http.HttpSessionAttributeListener;
import javax.servlet.http.HttpSessionBindingEvent;
import java.util.Enumeration;

public class DemoHttpSessionAttributeListener implements HttpSessionAttributeListener{
    public DemoHttpSessionAttributeListener() {
        System.out.println(">> MyHttpSessionAttributeListener - NEW");
    }

    @Override
    public void attributeAdded(HttpSessionBindingEvent httpSessionBindingEvent) {
        Enumeration names = httpSessionBindingEvent.getSession().getAttributeNames();
        while (names.hasMoreElements()) {
            System.out.println(">> Session - attribute added " + names.nextElement());
        }

    }

    @Override
    public void attributeRemoved(HttpSessionBindingEvent httpSessionBindingEvent) {
        Enumeration names = httpSessionBindingEvent.getSession().getAttributeNames();
        while (names.hasMoreElements()) {
            System.out.println(">> Session - attribute removed " + names.nextElement());
        }
    }

    @Override
    public void attributeReplaced(HttpSessionBindingEvent httpSessionBindingEvent) {
        Enumeration names = httpSessionBindingEvent.getSession().getAttributeNames();
        while (names.hasMoreElements()) {
            System.out.println(">> Session - attribute replaced " + names.nextElement());
        }
    }
}
