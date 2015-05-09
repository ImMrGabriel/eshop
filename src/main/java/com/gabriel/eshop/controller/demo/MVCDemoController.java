package com.gabriel.eshop.controller.demo;

import com.gabriel.eshop.entity.demo.DemoEntityA;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class MVCDemoController extends HttpServlet{
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //add to REQUEST attributes
        request.setAttribute("requestAttribute", new DemoEntityA());
        //add to SESSION attributes
        request.getSession().setAttribute("sessionAttribute", new DemoEntityA());
        // add to SERVLET CONTENT attributes
//        request.getServletContext()
//        request.getServletContent().setAttribute("servletContentAttribute", new MockEntityA())  //???

        request.setAttribute("test", "request");
        request.getSession().setAttribute("test", "session");
//        request.getServletContent().setAttribute("test", "servletContent");  // ???

        request.getRequestDispatcher("mvcMockView.jsp").forward(request, response);
    }
}
