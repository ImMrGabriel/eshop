package com.gabriel.eshop.dao.impl.jdbc.tx;

import com.gabriel.eshop.dao.impl.jdbc.JdbcUtils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.util.concurrent.Callable;

public class TransactionManagerImpl extends BaseDataSource implements TransactionManager{
    public final static String JDBC_URL = "jdbc:oracle:thin:@localhost:1521:orcl";
    public final static String JDBC_USER = "hr";
    public final static String JDBC_PASSWORD = "hr";
    public static final String JDBC_DRIVER = "oracle.jdbc.driver.OracleDriver";

    private static ThreadLocal<Connection> connectionHolder = new ThreadLocal<>();

    @Override
    public <T> T doInTransaction(Callable<T> unitOfWork) throws Exception {
        Class.forName(JDBC_DRIVER);
        Connection conn = DriverManager.getConnection(JDBC_URL, JDBC_USER, JDBC_PASSWORD);

        conn.setAutoCommit(false);
        connectionHolder.set(conn);
        try{
            T result = unitOfWork.call();
            conn.commit();
            return result;
        } catch (Exception e) {
            conn.rollback();
            throw e;
        } finally {
            JdbcUtils.closeQuietly(conn);
            connectionHolder.remove();
        }
    }

    @Override
    public Connection getConnection() {
        return connectionHolder.get();
    }
}
