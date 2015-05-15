package com.gabriel.eshop.dao.impl.jdbc;

import com.gabriel.eshop.dao.ProductDao;
import com.gabriel.eshop.dao.exception.DaoSystemException;
import com.gabriel.eshop.dao.exception.NoSuchEntityException;
import com.gabriel.eshop.entity.Product;

import java.sql.*;
import java.util.LinkedList;
import java.util.List;

public class ProductDaoJdbcImpl implements ProductDao{
    public final static String JDBC_URL = "jdbc:oracle:thin:@localhost:1521:orcl";
    public final static String JDBC_USER = "hr";
    public final static String JDBC_PASSWORD = "hr";
    public static final String JDBC_DRIVER = "oracle.jdbc.driver.OracleDriver";

    public final static String SELECT_ALL_SQL = "SELECT product_id, product_name FROM products";
    public final static String SELECT_BY_ID_SQL = "SELECT product_id, product_name FROM products WHERE product_id = ?";

    /**
     * Never return null!
     */
    @Override
    public Product selectById(int id) throws DaoSystemException, NoSuchEntityException {
        try {
            Class.forName(JDBC_DRIVER);
        } catch (ClassNotFoundException e) {
            throw new DaoSystemException(e.getMessage());
        }
        try(Connection conn = DriverManager.getConnection(JDBC_URL, JDBC_USER, JDBC_PASSWORD)) {
            conn.setAutoCommit(false);
            try(PreparedStatement stmt = conn.prepareStatement(SELECT_BY_ID_SQL)) {
                stmt.setInt(1, id);
                try(ResultSet rs = stmt.executeQuery()) {
                    if(!rs.next()) {
                        throw new NoSuchEntityException("No Product for id");
                    }
                    Product result = new Product(rs.getInt("product_id"), rs.getString("product_name"));
                    conn.commit();
                    return result;
                }
            }

        } catch (SQLException e) {
            throw new DaoSystemException(e.getMessage());
        }
    }

    @Override
    public List<Product> selectAll() throws DaoSystemException {
        try {
            Class.forName(JDBC_DRIVER);
        } catch (ClassNotFoundException e) {
            throw new DaoSystemException(e.getMessage());
        }
        List<Product> result = new LinkedList<>();
        try(Connection conn = DriverManager.getConnection(JDBC_URL, JDBC_USER, JDBC_PASSWORD)) {
            conn.setAutoCommit(false);
            try(Statement stmt = conn.createStatement();
                ResultSet rs = stmt.executeQuery(SELECT_ALL_SQL)) {
                while (rs.next()) {
                    result.add(new Product(rs.getInt("product_id"), rs.getString("product_name")));
                }
                conn.commit();
            }
        } catch (SQLException e) {
            throw new DaoSystemException(e.getMessage());
        }
        return result;
    }
}
