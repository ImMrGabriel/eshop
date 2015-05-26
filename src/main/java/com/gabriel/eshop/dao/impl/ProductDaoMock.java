package com.gabriel.eshop.dao.impl;

import com.gabriel.eshop.dao.ProductDao;
import com.gabriel.eshop.dao.exception.DaoSystemException;
import com.gabriel.eshop.dao.exception.NoSuchEntityException;
import com.gabriel.eshop.entity.Product;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Demo Data Access Object of the Product entity.
 * Data hardcoded in constructor and stored into ConcurrentHashMap
 */
public class ProductDaoMock implements ProductDao {
    private final Map<Integer, Product> memory = new ConcurrentHashMap<>();

    public ProductDaoMock() {
        this.memory.put(1, new Product(1, "Bread"));
        this.memory.put(2, new Product(2, "Paper"));
        this.memory.put(3, new Product(3, "Sugar"));
    }

    /**
     * Select product associated by the id
     * @id - identification, it is currently key of ConcurrentHashMap
     * @return the product (Never return NULL!)
     * @throws DaoSystemException
     * @throws NoSuchEntityException
     */
    @Override
    public Product selectById(int id) throws DaoSystemException, NoSuchEntityException {
        if(!memory.containsKey(id)) {
            throw new NoSuchEntityException("No Product for id == '" + id + "', only for " + memory.keySet());
        }
        return memory.get(id);
    }

    /**
     * Select list of all products, which there are at the ConcurrentHashMap
     * @return the products (Never return NULL!)
     * @throws DaoSystemException
     */
    @Override
    public List<Product> selectAll() throws DaoSystemException {
        return new ArrayList<>(memory.values());
    }
}
