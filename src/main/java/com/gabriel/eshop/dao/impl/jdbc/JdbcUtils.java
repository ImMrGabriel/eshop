package com.gabriel.eshop.dao.impl.jdbc;

import java.sql.Connection;

/**
 * Utils for work with JDBC.
 */
public class JdbcUtils {

    // without inheritance
    private JdbcUtils() {
    }

    /**
     * Gets the object of AutoCloseable and closes it, without throws exceptions
     * @param resource
     */
    public static void closeQuietly(AutoCloseable resource) {
        if (resource != null) {
            try {
                resource.close();
            } catch (Exception e) {
                // NOP
            }
        }
    }

    /**
     * Gets the Connection and closes it, without throws exceptions
     * @param conn
     */
    public static void rollbackQuietly(Connection conn) {
        if (conn != null) {
            try {
                conn.rollback();
            } catch (Exception e) {
                // NOP
            }
        }
    }

    /**
     * Gets the objects of AutoCloseable and closes them, without throws exceptions
     * @param resources
     */
    public static void closeQuietly(AutoCloseable... resources) {
        for(AutoCloseable resource : resources) {
            closeQuietly(resource);
        }
    }
}
