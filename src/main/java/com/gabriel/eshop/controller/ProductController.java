package com.gabriel.eshop.controller;

import com.gabriel.eshop.dao.ProductDao;
import com.gabriel.eshop.dao.exception.DaoSystemException;
import com.gabriel.eshop.dao.exception.NoSuchEntityException;
import com.gabriel.eshop.dao.impl.ProductDaoMock;
import com.gabriel.eshop.entity.Product;
import com.gabriel.eshop.inject.DependencyInjectionServlet;
import com.gabriel.eshop.inject.Inject;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @WebSocket("/product.do")
 * The servlet selects one product. And transmits the selected product
 * in the request attribute by ATTRIBUTE_MODEL_TO_VIEW.
 * Inherits from DependencyInjectionServlet to obtain instances for marked
 * fields from outside.
 */
public class ProductController extends DependencyInjectionServlet {
    public static final String PARAM_ID = "id";
    public static final String ATTRIBUTE_MODEL_TO_VIEW = "product";
    public static final String PAGE_OK = "product.jsp";
    public static final String PAGE_ERROR = "error.jsp";

    /**
     * The field gets an instance from an external appContext file using @Inject by
     * id="productDao".
     */
    @Inject("productDao")
    private ProductDao productDao;
//    private ProductDao productDao = new ProductDaoMock();

    /**
     * Selects product by ID. And transmits the selected product in the
     * request attribute by ATTRIBUTE_MODEL_TO_VIEW.
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
                Integer id = Integer.valueOf(idStr);
                Product model = productDao.selectById(id);
                request.setAttribute(ATTRIBUTE_MODEL_TO_VIEW, model);
                //OK
                request.getRequestDispatcher(PAGE_OK).forward(request, response);
                return;
            } catch (NumberFormatException | NoSuchEntityException | DaoSystemException e) {
                /*NOP*/
            }
        }
        //FAIL
        response.sendRedirect(PAGE_ERROR);
    }
}
