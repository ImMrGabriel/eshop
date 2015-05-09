package com.gabriel.eshop.listener;

import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

public class DemoHttpSessionListener implements HttpSessionListener{
    public DemoHttpSessionListener() {
        System.out.println(">> MyHttpSessionListener - NEW");
    }

    @Override
    public void sessionCreated(HttpSessionEvent httpSessionEvent) {
        System.out.println(">> HttpSession - created, id = " + httpSessionEvent.getSession().getId());
    }

    @Override
    public void sessionDestroyed(HttpSessionEvent httpSessionEvent) {
        System.out.println(">> HttpSession - destroyed, id = " + httpSessionEvent.getSession().getId());
    }
}
