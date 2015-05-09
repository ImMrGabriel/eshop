package com.gabriel.eshop.controller;

import com.gabriel.eshop.custom_view_framework.CustomHttpSession;
import com.gabriel.eshop.custom_view_framework.CustomHttpSessionOnClientRepository;
import com.gabriel.eshop.dao.ProductDao;
import com.gabriel.eshop.dao.exception.DaoSystemException;
import com.gabriel.eshop.dao.exception.NoSuchEntityException;
import com.gabriel.eshop.dao.impl.ProductDaoMock;
import com.gabriel.eshop.entity.Product;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.Map;

import static com.gabriel.eshop.controller.SessionAttributes.PRODUCTS_IN_BUCKET;
import static java.util.Collections.unmodifiableMap;

public class ProductRemoveFromBucketController extends HttpServlet {
    public static final String PARAM_ID = "id";
    public static final String PARAM_REDIRECT_TO_ID = "redirectToId";
    public static final String PAGE_ERROR = "productAll.do";

    private ProductDao productDao = new ProductDaoMock();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String idStr = request.getParameter(PARAM_ID);
        if(idStr != null) {
            try{
                int id = Integer.parseInt(idStr);
                Product product = productDao.selectById(id);

                HttpSession session = request.getSession();
//                CustomHttpSession session = CustomHttpSessionOnClientRepository.getSession(request);

                Map<Product, Integer> oldBucket = (Map<Product, Integer>) session.getAttribute(PRODUCTS_IN_BUCKET);
                if(oldBucket != null && oldBucket.containsKey(product)) {
                    Map<Product, Integer> newBucket = new LinkedHashMap<>(oldBucket);
                    int count = newBucket.get(product);
                    if(count == 1) {
                        newBucket.remove(product);
                    } else {
                        newBucket.put(product, count - 1);
                    }
                    session.setAttribute(PRODUCTS_IN_BUCKET, unmodifiableMap(newBucket));

                    //OK
//                    CustomHttpSessionOnClientRepository.saveSession(response, session);
                    try{
                        String idRedirect = request.getParameter(PARAM_REDIRECT_TO_ID);
                        if(idRedirect != null) {
                            id = Integer.parseInt(idRedirect);
                        }
                    } catch (NumberFormatException e) {
                        /*NOP*/
                    }

                    String newLocation = "product.do?id=" + id;
                    response.sendRedirect(newLocation);
                    return;
                }
            } catch (NumberFormatException | NoSuchEntityException | DaoSystemException e) {
                /*NOP*/
            }
        }
        //FAIL
        response.sendRedirect(PAGE_ERROR);
    }
}