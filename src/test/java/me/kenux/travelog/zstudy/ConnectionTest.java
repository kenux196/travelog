package me.kenux.travelog.zstudy;

import com.zaxxer.hikari.HikariDataSource;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

@Slf4j
class ConnectionTest {

    private final String url = "jdbc:h2:mem:testdb;DATABASE_TO_LOWER=TRUE;DB_CLOSE_ON_EXIT=FALSE";
    private final String id = "sa";
    private final String password = "";

    @Test
    void driveManager() throws SQLException {
        final Connection con1 = DriverManager.getConnection(url, id, password);
        final Connection con2 = DriverManager.getConnection(url, id, password);

        log.info("connection = {}, class = {}", con1, con1.getClass());
        log.info("connection = {}, class = {}", con2, con2.getClass());
    }

    @Test
    void dataSourceDriverManager() throws SQLException {
        log.info("dataSourceDriverManager : start");
        final DriverManagerDataSource dataSource = new DriverManagerDataSource(url, id, password);
        log.info("dataSourceDriverManager : created dataSource");
        useDataSource(dataSource);
        log.info("dataSourceDriverManager : end");
    }

    @Test
    void dataSourceConnectionPool() throws SQLException, InterruptedException {
        log.info("dataSourceConnectionPool : start");
        final HikariDataSource dataSource = new HikariDataSource();
        log.info("dataSourceConnectionPool : created hikari dataSource");
        dataSource.setJdbcUrl(url);
        dataSource.setUsername(id);
        dataSource.setMaximumPoolSize(10);
        dataSource.setPoolName("myPool");
        log.info("dataSourceConnectionPool : configured dataSource");
        useDataSource(dataSource);
        Thread.sleep(1000);
    }


    private void useDataSource(DataSource dataSource) throws SQLException {
        log.info("useDataSource: start");
        final Connection con1 = dataSource.getConnection();
        final Connection con2 = dataSource.getConnection();

        log.info("connection = {}, class = {}", con1, con1.getClass());
        log.info("connection = {}, class = {}", con2, con2.getClass());
    }
}
