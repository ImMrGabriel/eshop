package com.gabriel.eshop.listener;


import javax.servlet.http.HttpSessionActivationListener;
import javax.servlet.http.HttpSessionEvent;

public class DemoHttpSessionActivationListener implements HttpSessionActivationListener{

    public DemoHttpSessionActivationListener() {
        System.out.println(">> MyHttpSessionActivationListener - NEW");
    }

    @Override
    public void sessionWillPassivate(HttpSessionEvent httpSessionEvent) {
        System.out.println(">> HttpSession - will passivate, id = " + httpSessionEvent.getSession().getId());
    }

    @Override
    public void sessionDidActivate(HttpSessionEvent httpSessionEvent) {
        System.out.println(">> HttpSession - did active, id = " + httpSessionEvent.getSession().getId());
    }
}
