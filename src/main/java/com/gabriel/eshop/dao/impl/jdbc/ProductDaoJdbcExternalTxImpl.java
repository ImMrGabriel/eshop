package com.gabriel.eshop.dao.impl.jdbc;

import com.gabriel.eshop.dao.ProductDao;
import com.gabriel.eshop.dao.exception.DaoSystemException;
import com.gabriel.eshop.dao.exception.NoSuchEntityException;
import com.gabriel.eshop.entity.Product;

import javax.sql.DataSource;
import java.sql.*;
import java.util.LinkedList;
import java.util.List;

public class ProductDaoJdbcExternalTxImpl implements ProductDao {

    public final static String SELECT_ALL_SQL = "SELECT product_id, product_name FROM products";
    public final static String SELECT_BY_ID_SQL = "SELECT product_id, product_name FROM products WHERE product_id = ?";

    private DataSource dataSource;

    public void setDataSource(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    /**
     * Never return null!
     */
    @Override
    public Product selectById(int id) throws DaoSystemException, NoSuchEntityException {
        PreparedStatement stmt = null;
        ResultSet rs = null;
        try{
            Connection conn = dataSource.getConnection();
            stmt = conn.prepareStatement(SELECT_BY_ID_SQL);
            stmt.setInt(1, id);
            rs = stmt.executeQuery();
            if(!rs.next()) {
                throw new NoSuchEntityException("No Product for id = " + id);
            }
            return new Product(rs.getInt("product_id"), rs.getString("product_name"));
        } catch (SQLException e) {
            throw new DaoSystemException(e.getMessage());
        } finally {
            JdbcUtils.closeQuietly(rs, stmt);
        }
    }

    @Override
    public List<Product> selectAll() throws DaoSystemException {
        List<Product> result = new LinkedList<>();
        try{
            Connection conn = dataSource.getConnection();
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
