package com.gabriel.eshop.dao;

import com.gabriel.eshop.dao.exception.DaoSystemException;
import com.gabriel.eshop.dao.exception.NoSuchEntityException;
import com.gabriel.eshop.entity.Product;

import java.util.List;

/**
 * Data Access Object of the Product entity.
 */
public interface ProductDao {

    /**
     * Select product associated by the id
     * @id - identification
     * @return the product (Never return NULL!)
     * @throws DaoSystemException
     * @throws NoSuchEntityException
     */
    public Product selectById(int id) throws DaoSystemException, NoSuchEntityException;

    /**
     * Select list of all products, which there are at the storage
     * @return the products (Never return NULL!)
     * @throws DaoSystemException
     */
    public List<Product> selectAll() throws DaoSystemException;
}
