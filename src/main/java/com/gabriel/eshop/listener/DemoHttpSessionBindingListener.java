package com.gabriel.eshop.listener;

import javax.servlet.http.HttpSessionBindingEvent;
import javax.servlet.http.HttpSessionBindingListener;

public class DemoHttpSessionBindingListener implements HttpSessionBindingListener{

    public DemoHttpSessionBindingListener() {
        System.out.println(">> MyHttpSessionBindingListener - NEW");
    }

    @Override
    public void valueBound(HttpSessionBindingEvent httpSessionBindingEvent) {
        System.out.println(">> Session - value bound");
    }

    @Override
    public void valueUnbound(HttpSessionBindingEvent httpSessionBindingEvent) {
        System.out.println(">> Session - value unbound");
    }
}
