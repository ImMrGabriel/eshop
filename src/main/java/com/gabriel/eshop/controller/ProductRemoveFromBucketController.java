package com.gabriel.eshop.controller;

import com.gabriel.eshop.custom_view_framework.CustomHttpSession;
import com.gabriel.eshop.custom_view_framework.CustomHttpSessionOnClientRepository;
import com.gabriel.eshop.dao.ProductDao;
import com.gabriel.eshop.dao.exception.DaoSystemException;
import com.gabriel.eshop.dao.exception.NoSuchEntityException;
import com.gabriel.eshop.entity.Product;
import com.gabriel.eshop.inject.DependencyInjectionServlet;
import com.gabriel.eshop.inject.Inject;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.Map;

import static com.gabriel.eshop.controller.SessionAttributes.PRODUCTS_IN_BUCKET;
import static java.util.Collections.unmodifiableMap;

/**
 * @WebSocket("/productRemoveFromBucket.do")
 * The servlet removes the specified product by ID from the bucket.
 * Inherits from DependencyInjectionServlet to obtain instances for marked fields
 * from outside.
 *
 */
public class ProductRemoveFromBucketController extends DependencyInjectionServlet {
    public static final String PARAM_ID = "id";
    public static final String PARAM_REDIRECT_TO_ID = "redirectToId";
    public static final String PAGE_ERROR = "productAll.do";

    /**
     * The field gets an instance from an external appContext file using @Inject by
     * id="productDao".
     */
    @Inject("productDao")
    private ProductDao productDao;
//    private ProductDao productDao = new ProductDaoMock();

    /**
     * Removes the specified product from the bucket.
     * For this, it selects the required product by ID. Then from the attributes of
     * the session gets old bucket. And copies it into a new not thread safe map.
     * Also in the new map removes the specified product. And if all went well then in
     * the session attributes replace the old card with a new unmodified map.
     * And if the request parameters specified PARAM_REDIRECT_TO_ID, then redirects to
     * the product page with the given ID. Otherwise redirected to the product page with
     * the removed product ID.
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
