package com.gabriel.eshop.controller;

import com.gabriel.eshop.custom_view_framework.CustomHttpSession;
import com.gabriel.eshop.custom_view_framework.CustomHttpSessionOnClientRepository;
import com.gabriel.eshop.dao.ProductDao;
import com.gabriel.eshop.dao.exception.DaoSystemException;
import com.gabriel.eshop.dao.exception.NoSuchEntityException;
import com.gabriel.eshop.dao.impl.ProductDaoMock;
import com.gabriel.eshop.entity.Product;
import com.gabriel.eshop.inject.DependencyInjectionServlet;
import com.gabriel.eshop.inject.Inject;

import static com.gabriel.eshop.controller.SessionAttributes.PRODUCTS_IN_BUCKET;
import static java.util.Collections.singletonMap;
import static java.util.Collections.unmodifiableMap;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.Map;

public class ProductAddToBucketController extends DependencyInjectionServlet {
    public static final String PARAM_ID = "id";
    public static final String PAGE_ERROR = "productAll.do";

    @Inject("productDao")
    private ProductDao productDao;
//    private ProductDao productDao = new ProductDaoMock();


    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String idStr = request.getParameter(PARAM_ID);
        if(idStr != null) {
            try{
                Integer id = Integer.valueOf(idStr);
                Product product = productDao.selectById(id);

                HttpSession session = request.getSession(true);
//                CustomHttpSession session = CustomHttpSessionOnClientRepository.getSession(request);

                Map<Product, Integer> oldBucket = (Map<Product, Integer>) session.getAttribute(PRODUCTS_IN_BUCKET);
                if(oldBucket == null) {
                    session.setAttribute(PRODUCTS_IN_BUCKET, singletonMap(product, 1));
                } else {
                    Map<Product, Integer> newBucket = new LinkedHashMap<>(oldBucket);
                    if(!newBucket.containsKey(product)) {
                        newBucket.put(product, 1);
                    } else {
                        newBucket.put(product, newBucket.get(product) + 1);
                    }
                    session.setAttribute(PRODUCTS_IN_BUCKET, unmodifiableMap(newBucket));
                }
                //OK
//                CustomHttpSessionOnClientRepository.saveSession(response, session);
//                request.setAttribute(PRODUCTS_IN_BUCKET, session.getAttribute(PRODUCTS_IN_BUCKET));

                String newLocation = "product.do?id=" + id;
                response.sendRedirect(newLocation);
                return;
            } catch (NumberFormatException | NoSuchEntityException | DaoSystemException e) {
                /*NOP*/
            }
        }
        //FAIL
        response.sendRedirect(PAGE_ERROR);
    }
}
