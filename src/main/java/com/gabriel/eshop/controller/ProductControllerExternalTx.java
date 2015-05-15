package com.gabriel.eshop.controller;

import com.gabriel.eshop.dao.ProductDao;
import com.gabriel.eshop.dao.exception.DaoSystemException;
import com.gabriel.eshop.dao.exception.NoSuchEntityException;
import com.gabriel.eshop.dao.impl.jdbc.tx.TransactionManager;
import com.gabriel.eshop.entity.Product;
import com.gabriel.eshop.inject.DependencyInjectionServlet;
import com.gabriel.eshop.inject.Inject;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.concurrent.Callable;

public class ProductControllerExternalTx extends DependencyInjectionServlet {
    public static final String PARAM_ID = "id";
    public static final String ATTRIBUTE_MODEL_TO_VIEW = "product";
    public static final String PAGE_OK = "product.jsp";
    public static final String PAGE_ERROR = "error.jsp";

    @Inject("txManager")
    private TransactionManager txManager;

    @Inject("productDao")
    private ProductDao productDao;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String idStr = request.getParameter(PARAM_ID);
        if(idStr != null) {
            try{
                final Integer id = Integer.valueOf(idStr);
                Callable<Product> unitOfWork = new Callable<Product>() {
                    @Override
                    public Product call() throws DaoSystemException, NoSuchEntityException {
                        return productDao.selectById(id);
                    }
                };
                Product model = txManager.doInTransaction(unitOfWork);
//                Product model = productDao.selectById(id);
                request.setAttribute(ATTRIBUTE_MODEL_TO_VIEW, model);
                //OK
                request.getRequestDispatcher(PAGE_OK).forward(request, response);
                return;
            } catch (Exception /* NumberFormatException | NoSuchEntityException | DaoSystemException*/ e) {
                /*NOP*/
            }
        }
        //FAIL
        response.sendRedirect(PAGE_ERROR);
    }
}
