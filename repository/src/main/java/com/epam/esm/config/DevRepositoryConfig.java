package com.epam.esm.config;

import org.apache.commons.dbcp2.*;
import org.apache.commons.pool2.ObjectPool;
import org.apache.commons.pool2.impl.GenericObjectPool;
import org.apache.commons.pool2.impl.GenericObjectPoolConfig;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.*;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;
import java.util.Properties;

/**
 * Configuration class for setting up database-related beans and properties.
 */
@Configuration
@Profile("dev")
@EnableTransactionManagement
@ComponentScan(basePackages = "com.epam.esm")
@PropertySource("classpath:application-dev.properties")
public class DevRepositoryConfig {

    @Value("${db.url}")
    private String url;

    @Value("${db.username}")
    private String username;

    @Value("${db.password}")
    private String password;

    @Value("${db.driver}")
    private String driver;


    /**
     * Creates a basic data source for connecting to the database.
     *
     * @return a {@link javax.sql.DataSource} object representing the data source
     */
    @Bean
    public DataSource dataSource() {
        BasicDataSource dataSource = new BasicDataSource();
        dataSource.setInitialSize(10);
        dataSource.setDriverClassName(driver);
        dataSource.setUrl(url);
        dataSource.setUsername(username);
        dataSource.setPassword(password);
        return dataSource;
    }

    /**
     * Creates a transaction manager for managing transactions with the database.
     *
     * @param dataSource the data source to be used for managing transactions
     * @return a {@link org.springframework.jdbc.datasource.DataSourceTransactionManager} object representing the transaction manager
     */
    @Bean
    public DataSourceTransactionManager dataSourceTransactionManager(DataSource dataSource) {
        return new DataSourceTransactionManager(dataSource);
    }

    /**
     * Creates a {@link org.springframework.jdbc.core.JdbcTemplate} object for executing SQL statements against the database.
     *
     * @return a {@link org.springframework.jdbc.core.JdbcTemplate} object
     */
    @Bean
    public JdbcTemplate jdbcTemplate() {
        DataSource dataSource = dataSource();
        return new JdbcTemplate(dataSource);
    }

}