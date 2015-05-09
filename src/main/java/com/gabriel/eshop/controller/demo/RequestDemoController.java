package com.gabriel.eshop.controller.demo;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

public class RequestDemoController extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html");
        try(PrintWriter writer = response.getWriter()) {
            writer.println("request.getAuthType() = " + request.getAuthType());
            writer.println("request.getContextPath() = " + request.getContextPath());
            writer.println("request.getPathInfo() = " + request.getPathInfo());
            writer.println("request.getPathTranslated() = " + request.getPathTranslated());
            writer.println("request.getQueryString() = " + request.getQueryString());
            writer.println("request.getRemoteUser() = " + request.getRemoteUser());
            writer.println("request.getRequestedSessionId() = " + request.getRequestedSessionId());
            writer.println("request.getRequestURI() = " + request.getRequestURI());
            writer.println("request.getLocalAddr() = " + request.getLocalAddr());
            writer.println("request.getLocalName() = " + request.getLocalName());
            writer.println("request.getRemoteAddr() = " + request.getRemoteAddr());
            writer.println("request.getRemoteHost() = " + request.getRemoteHost());
            writer.flush();
        }
    }
}
