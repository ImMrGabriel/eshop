package com.gabriel.eshop.listener;

import javax.servlet.ServletRequestEvent;
import javax.servlet.ServletRequestListener;

public class DemoServletRequestListener implements ServletRequestListener {

    public DemoServletRequestListener() {
        System.out.println(">> MyServletRequestListener - NEW");
    }

    @Override
    public void requestDestroyed(ServletRequestEvent servletRequestEvent) {
        System.out.println(">> ServletRequest - destroyed");
    }

    @Override
    public void requestInitialized(ServletRequestEvent servletRequestEvent) {
        System.out.println(">> ServletRequest - initialized");
    }
}
