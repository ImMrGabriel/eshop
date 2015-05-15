package com.gabriel.eshop.controller;

import com.gabriel.eshop.dao.ProductDao;
import com.gabriel.eshop.dao.exception.DaoException;
import com.gabriel.eshop.dao.exception.DaoSystemException;
import com.gabriel.eshop.dao.exception.NoSuchEntityException;
import com.gabriel.eshop.dao.impl.jdbc.tx.TransactionManager;
import com.gabriel.eshop.dao.impl.jdbc.tx.UnitOfWork;
import com.gabriel.eshop.entity.Product;
import com.gabriel.eshop.inject.DependencyInjectionServlet;
import com.gabriel.eshop.inject.Inject;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.concurrent.Callable;

public class ProductAllControllerExternalTx extends DependencyInjectionServlet {
    public static final String PARAM_ID = "id";
    public static final String ATTRIBUTE_MODEL_TO_VIEW = "productList";
    public static final String PAGE_OK = "productAll.jsp";
    public static final String PAGE_ERROR = "error.jsp";

    @Inject("txManager")
    private TransactionManager txManager;

    @Inject("productDao")
    private ProductDao productDao;


    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            UnitOfWork<List<Product>, DaoSystemException> unitOfWork = new UnitOfWork<List<Product>, DaoSystemException>() {
                @Override
                public List<Product> doInTx() throws DaoSystemException {
                    return productDao.selectAll();
                }
            };
            List<Product> model = txManager.doInTransaction(unitOfWork);
            request.setAttribute(ATTRIBUTE_MODEL_TO_VIEW, model);
            //OK
            RequestDispatcher dispatcher = request.getRequestDispatcher(PAGE_OK);
            dispatcher.forward(request, response);
            return;
        } catch (DaoSystemException ignore) {
            /*NOP*/
        }
        //FAIL
        response.sendRedirect(PAGE_ERROR);
    }
}
