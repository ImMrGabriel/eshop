package com.gabriel.eshop.dao.impl.jdbc.tx;

import com.gabriel.eshop.dao.impl.jdbc.BaseDataSource;
import com.gabriel.eshop.dao.impl.jdbc.JdbcUtils;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.concurrent.Callable;

/**
 * This class is a DataSource,  that it is able to distribute connections for each thread,
 * there is only one specific connection. It also implements the logic of the transaction
 * for a specified piece of work. *
 */
public class TransactionManagerImpl extends BaseDataSource implements TransactionManager{

    /**
     * Field to get the same connection for the same thread
     */
    private static ThreadLocal<Connection> connectionHolder = new ThreadLocal<>();

    /**
     * Field dataSource gives DataSource from an external file (see resource/*.xml).
     */
    private DataSource dataSource;

    public void setDataSource(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    /**
     * The method gets a piece of work which should be done in a transaction.
     * @param unitOfWork it is a generic parameter that specifies the return class and
     *                   the exception for unwanted behavior.
     * @param <T> class of result
     * @param <E>
     * @return <T>(<never return NULL!)
     * @throws E
     */
    @Override
    public <T, E extends Exception> T doInTransaction(UnitOfWork<T, E> unitOfWork) throws E {
        Connection conn = null;
        try {
            // get connection for current thread
            conn = dataSource.getConnection();
            conn.setAutoCommit(false);
            // remember the connection in ThreadLocal for future use
            connectionHolder.set(conn);
            // execute the piece of work and get a result or an exception
            T result = unitOfWork.doInTx();
            // successful
            conn.commit();
            return result;
        } catch (Exception e) {
            // not successful and rollback
            JdbcUtils.rollbackQuietly(conn);
        } finally {
            // we opened it and we have close it
            JdbcUtils.closeQuietly(conn);
            // remove the connection from ThreadLocal
            connectionHolder.remove();
        }
        return null;
    }

    /**
     * @return the connection for specified thread
     */
    @Override
    public Connection getConnection() {
        return connectionHolder.get();
    }

}
