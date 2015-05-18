package com.gabriel.eshop.dao.impl.jdbc.pool;

import com.gabriel.eshop.dao.impl.jdbc.BaseDataSource;
import com.jolbox.bonecp.BoneCPDataSource;
//import org.apache.log4j.Logger;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.concurrent.TimeUnit;
//import java.util.logging.Logger;

//import static com.gabriel.util.ClassName.getCurrentClassName;

public class BCPDataSource extends BaseDataSource implements DataSource {
//    private static final Logger logger = Logger.getLogger(getCurrentClassName());

    private String driverClass;
    private String jdbcUrl;
    private String user;
    private String password;
    private BoneCPDataSource dataSource;

    public void setDriverClass(String driverClass) {
        this.driverClass = driverClass;
    }

    public void setJdbcUrl(String jdbcUrl) {
        this.jdbcUrl = jdbcUrl;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void init() throws SQLException {
        dataSource = new BoneCPDataSource();
        //jdbc
        dataSource.setDriverClass(driverClass);
        dataSource.setJdbcUrl(jdbcUrl);
        dataSource.setUser(user);
        dataSource.setPassword(password);
        //
        dataSource.setAcquireIncrement(4);  // per partition settings
        dataSource.setAcquireRetryAttempts(5);
        dataSource.setAcquireRetryDelayInMs(100);
        dataSource.setStatementsCacheSize(200);
        // development mode
        dataSource.setDetectUnclosedStatements(true);
        dataSource.setCloseOpenStatements(true);
        dataSource.setDetectUnresolvedTransactions(true);
        dataSource.setLogStatementsEnabled(true);
        //
        dataSource.setTransactionRecoveryEnabled(false);
        dataSource.setStatisticsEnabled(false);
        // test idle connection
        dataSource.setConnectionTestStatement("/* ping */ SELECT 1");   //http://blogs.sun.com/SDNChannel/entry/mysql_tips_for_java_developers
        dataSource.setIdleConnectionTestPeriod(1, TimeUnit.MINUTES);
        // pool
        dataSource.setPoolStrategy("CACHED");   //Connection (or pool?) to every thread (ThreadLocal)
        // OR
//        dataSource.setPoolStrategy("DEFAULT");  // One connection pool to every partition of threads
//        dataSource.setPartitionCount(4);
//        dataSource.setMinConnectionsPerPartition(5);
//        dataSource.setMaxConnectionsPerPartition(10);

        dataSource.setLazyInit(true);
        dataSource.setIdleMaxAgeInMinutes(10);
    }

    @Override
    public Connection getConnection() throws SQLException {
        return dataSource.getConnection();
    }

    public void shutdown() {
        dataSource.close();
    }
}
