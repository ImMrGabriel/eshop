package com.gabriel.eshop.dao.impl.jdbc;

import com.gabriel.eshop.dao.ProductDao;
import com.gabriel.eshop.dao.exception.DaoSystemException;
import com.gabriel.eshop.dao.exception.NoSuchEntityException;
import com.gabriel.eshop.entity.Product;

import javax.sql.DataSource;
import java.sql.*;
import java.util.LinkedList;
import java.util.List;

/**
 * JDBC implementation Data Access Object of the Product entity.
 * Uses external transaction. Field dataSource gives connections.
 * Each thread has only one connection.
 * The connections will be closed from the outside at the end of the transaction in any case.
 * Spring bean sets the DataSource instance in the dataSource.
 * It is important, the setter of DataSource must be!
 */
public class ProductDaoJdbcExternalTxImpl implements ProductDao {

    public final static String SELECT_ALL_SQL = "SELECT product_id, product_name FROM products";
    public final static String SELECT_BY_ID_SQL = "SELECT product_id, product_name FROM products WHERE product_id = ?";

    /**
     * External transaction (see resource/*.xml)
     */
    private DataSource dataSource;

    /**
     * Spring bean sets the DataSource instance
     * @param dataSource
     */
    public void setDataSource(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    /**
     * Select product associated by the id.
     * Uses a PreparedStatement, with the specified id.
     * Gets the Connection from the DataSource instance.
     * Importantly, we opened the PreparedStatement and the ResultSet and we have close them.
     * And the Connection will be closed from the outside at the end of the transaction.
     * @id - identification, it is currently primary key database
     * @return the product (Never return NULL!)
     * @throws DaoSystemException
     * @throws NoSuchEntityException
     */
    @Override
    public Product selectById(int id) throws DaoSystemException, NoSuchEntityException {
        PreparedStatement stmt = null;
        ResultSet rs = null;
        try{
            // get connection for current thread
            Connection conn = dataSource.getConnection();
            stmt = conn.prepareStatement(SELECT_BY_ID_SQL);
            stmt.setInt(1, id);
            rs = stmt.executeQuery();
            if(!rs.next()) {
                // the calling method will be called rollback
                throw new NoSuchEntityException("No Product for id = " + id);
            }
            // the calling method will be called commit
            return new Product(rs.getInt("product_id"), rs.getString("product_name"));
        } catch (SQLException e) {
            // the calling method will be called rollback
            throw new DaoSystemException(e.getMessage());
        } finally {
            // we opened them and we have close them
            JdbcUtils.closeQuietly(rs, stmt);
        }
    }

    /**
     * Select list of all products from the specified database.
     * Uses a usually Statement.
     * Gets the Connection from the DataSource instance.
     * Importantly, we opened the Statement and the ResultSet and we have close them.
     * And the Connection will be closed from the outside at the end of the transaction.
     * @return the products (Never return NULL!)
     * @throws DaoSystemException
     */
    @Override
    public List<Product> selectAll() throws DaoSystemException {
        List<Product> result = new LinkedList<>();
        try{
            // get connection for current thread
            Connection conn = dataSource.getConnection();
            try(Statement stmt = conn.createStatement();
                ResultSet rs = stmt.executeQuery(SELECT_ALL_SQL)) {
                while (rs.next()) {
                    result.add(new Product(rs.getInt("product_id"), rs.getString("product_name")));
                }
                // the calling method will be called commit
                return result;
            }
        } catch (SQLException e) {
            // the calling method will be called rollback
            throw new DaoSystemException(e.getMessage());
        }
    }
}
