package com.gabriel.eshop.dao.impl.jdbc.tx;

import com.gabriel.eshop.dao.impl.jdbc.BaseDataSource;
import com.gabriel.eshop.dao.impl.jdbc.JdbcUtils;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.concurrent.Callable;

public class TransactionManagerImpl extends BaseDataSource implements TransactionManager{

    private static ThreadLocal<Connection> connectionHolder = new ThreadLocal<>();

    private DataSource dataSource;

    public void setDataSource(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public <T, E extends Exception> T doInTransaction(UnitOfWork<T, E> unitOfWork) throws E {
        Connection conn = null;
        try {
            conn = dataSource.getConnection();
            conn.setAutoCommit(false);
            connectionHolder.set(conn);
            T result = unitOfWork.doInTx();
            conn.commit();
            return result;
        } catch (Exception e) {
            JdbcUtils.rollbackQuietly(conn);
        } finally {
            JdbcUtils.closeQuietly(conn);
            connectionHolder.remove();
        }
        return null;
    }

    @Override
    public Connection getConnection() {
        return connectionHolder.get();
    }

}
