package com.gabriel.eshop.dao.impl.jdbc;

import com.gabriel.eshop.dao.ProductDao;
import com.gabriel.eshop.dao.exception.DaoSystemException;
import com.gabriel.eshop.dao.exception.NoSuchEntityException;
import com.gabriel.eshop.entity.Product;

import java.sql.*;
import java.util.LinkedList;
import java.util.List;

/**
 * JDBC implementation Data Access Object of the Product entity.
 * Oracle as a database. The Oracle jdbc driver ojdbc6 must be installed (dependency Maven).
 * Makes connection through DriverManager.getConnection().
 */
public class ProductDaoJdbcImpl implements ProductDao{
    public final static String JDBC_URL = "jdbc:oracle:thin:@localhost:1521:orcl";
    public final static String JDBC_USER = "hr";
    public final static String JDBC_PASSWORD = "hr";
    public static final String JDBC_DRIVER = "oracle.jdbc.driver.OracleDriver";

    public final static String SELECT_ALL_SQL = "SELECT product_id, product_name FROM products";
    public final static String SELECT_BY_ID_SQL = "SELECT product_id, product_name FROM products WHERE product_id = ?";

    /**
     * Select product associated by the id.
     * Uses a PreparedStatement, with the specified id.
     * And a small imitation of the transaction.
     * Importantly, we opened the connection and we have close it.
     * @id - identification, it is currently primary key database
     * @return the product (Never return NULL!)
     * @throws DaoSystemException
     * @throws NoSuchEntityException
     */
    @Override
    public Product selectById(int id) throws DaoSystemException, NoSuchEntityException {
        try {
            Class.forName(JDBC_DRIVER);
        } catch (ClassNotFoundException e) {
            throw new DaoSystemException(e.getMessage());
        }
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        try{
            conn = DriverManager.getConnection(JDBC_URL, JDBC_USER, JDBC_PASSWORD);
            // transaction starts
            conn.setAutoCommit(false);
            stmt = conn.prepareStatement(SELECT_BY_ID_SQL);
            stmt.setInt(1, id);
            rs = stmt.executeQuery();
            if(!rs.next()) {
                throw new NoSuchEntityException("No Product for id = " + id);
            }
            Product result = new Product(rs.getInt("product_id"), rs.getString("product_name"));
            // transaction is successful, otherwise threw an exception
            conn.commit();
            return result;
        } catch (SQLException e) {
            // transaction is not successful, rollback and throw an exception
            JdbcUtils.rollbackQuietly(conn);
            throw new DaoSystemException(e.getMessage());
        } finally {
            // we opened them and we have close them
            JdbcUtils.closeQuietly(rs, stmt, conn);
        }
    }

    /**
     * Select list of all products from the specified database.
     * Uses a usually Statement.
     * And a small imitation of the transaction.
     * Importantly, we opened the connection and we have close it.
     * @return the products (Never return NULL!)
     * @throws DaoSystemException
     */
    @Override
    public List<Product> selectAll() throws DaoSystemException {
        try {
            Class.forName(JDBC_DRIVER);
        } catch (ClassNotFoundException e) {
            throw new DaoSystemException(e.getMessage());
        }
        List<Product> result = new LinkedList<>();
        Connection conn = null;
        try {
            conn = DriverManager.getConnection(JDBC_URL, JDBC_USER, JDBC_PASSWORD);
            // transaction starts
            conn.setAutoCommit(false);
            try(Statement stmt = conn.createStatement();
                ResultSet rs = stmt.executeQuery(SELECT_ALL_SQL)) {
                while (rs.next()) {
                    result.add(new Product(rs.getInt("product_id"), rs.getString("product_name")));
                }
                // transaction is successful, otherwise threw an exception
                conn.commit();
                return result;
            }
        } catch (SQLException e) {
            // transaction is not successful, roll back and throw an exception
            JdbcUtils.rollbackQuietly(conn);
            throw new DaoSystemException(e.getMessage());
        } finally {
            // we opened it and we have close it
            JdbcUtils.closeQuietly(conn);
        }
    }
}
