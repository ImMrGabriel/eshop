package com.gabriel.eshop.dao.impl.jdbc;

import java.sql.*;

public class JdbcUtils {
    private JdbcUtils() {
    }

    public static void closeQuietly(ResultSet rs) {
        if (rs != null) {
            try {
                rs.close();
            } catch (SQLException e) {
                // NOP
            }
        }
    }

    public static void closeQuietly(Statement ps) {
        if (ps != null) {
            try {
                ps.close();
            } catch (SQLException e) {
                // NOP
            }
        }
    }

    public static void closeQuietly(Connection conn) {
        if (conn != null) {
            try {
                conn.close();
            } catch (SQLException e) {
                // NOP
            }
        }
    }

    public static void rollbackQuietly(Connection conn) {
        if (conn != null) {
            try {
                conn.rollback();
            } catch (SQLException e) {
                // NOP
            }
        }
    }

    public static void closeQuietly(ResultSet rs, Statement stmt) {
        closeQuietly(rs);
        closeQuietly(stmt);
    }
}
