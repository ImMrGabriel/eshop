package com.gabriel.eshop.controller.demo;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class CookieDemoController extends HttpServlet{
    private final static String COOKIE_NAME = "counter";

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Cookie[] cookies = request.getCookies();
        Cookie fromClient = null;
        if(cookies != null) {
            for (Cookie cookie : cookies) {
                if (COOKIE_NAME.equals(cookie.getName())) {
                    fromClient = cookie;
                    break;
                }
            }
        }
        if(fromClient == null) {
            response.addCookie(new Cookie(COOKIE_NAME, "1"));
            response.getWriter().write("You visit this page: 1 time");
        } else {
            int visitCount = Integer.valueOf(fromClient.getValue());
            response.addCookie(new Cookie(COOKIE_NAME, String.valueOf(++visitCount)));
            response.getWriter().write("You visit this page: " + visitCount + " times");
        }
    }
}
