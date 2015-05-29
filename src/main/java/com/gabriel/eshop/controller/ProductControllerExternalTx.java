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
import java.util.concurrent.Callable;

/**
 * @WebSocket("/product.do")
 * The servlet selects one product, using the external transaction.
 * And transmits the selected product in the request attribute
 * by ATTRIBUTE_MODEL_TO_VIEW.
 * Inherits from DependencyInjectionServlet to obtain instances for
 * marked fields from outside.
 */
public class ProductControllerExternalTx extends DependencyInjectionServlet {
    public static final String PARAM_ID = "id";
    public static final String ATTRIBUTE_MODEL_TO_VIEW = "product";
    public static final String PAGE_OK = "product.jsp";
    public static final String PAGE_ERROR = "error.jsp";

    /**
     * The field gets an instance from an external appContext file using @Inject by
     * id="txManager".
     */
    @Inject("txManager")
    private TransactionManager txManager;

    /**
     * The field gets an instance from an external appContext file using @Inject by
     * id="productDao".
     */
    @Inject("productDao")
    private ProductDao productDao;

    /**
     * Selects product by ID, indicating the desired method as a piece of
     * work in an external transaction. And transmits the selected product
     * in the request attribute by ATTRIBUTE_MODEL_TO_VIEW.
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String idStr = request.getParameter(PARAM_ID);
        if(idStr != null) {
            try{
                final Integer id = Integer.valueOf(idStr);
                Product model = txManager.doInTransaction(new UnitOfWork<Product, DaoException>() {
                    @Override
                    public Product doInTx() throws DaoException {
                        return productDao.selectById(id);
                    }
                });
                request.setAttribute(ATTRIBUTE_MODEL_TO_VIEW, model);
                //OK
                RequestDispatcher dispatcher = request.getRequestDispatcher(PAGE_OK);
                dispatcher.forward(request, response);
                return;
            } catch (DaoException | NumberFormatException e) {
                e.printStackTrace();
            }
        }
        //FAIL
        response.sendRedirect(PAGE_ERROR);
    }
}
