package com.epam.esm.config;

import org.apache.commons.dbcp2.BasicDataSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;

/**

 Configuration class for setting up database-related beans and properties.
 */
@Configuration
@EnableTransactionManagement
@ComponentScan(basePackages = "com.epam.esm")
@PropertySource("classpath:application.properties")
public class RepositoryConfiguration {
    /**

     Creates a basic data source for connecting to the database.
     @return a {@link javax.sql.DataSource} object representing the data source
     */
    @Bean
    public DataSource dataSource() {
        BasicDataSource dataSource = new BasicDataSource();
        dataSource.setInitialSize(10);
        dataSource.setDriverClassName("org.postgresql.Driver");
        dataSource.setUrl("jdbc:postgresql://localhost:5432/gift_certificate");
        dataSource.setUsername("postgres");
        dataSource.setPassword("root123");
        return dataSource;
    }
    /**

     Creates a transaction manager for managing transactions with the database.
     @param dataSource the data source to be used for managing transactions
     @return a {@link org.springframework.jdbc.datasource.DataSourceTransactionManager} object representing the transaction manager
     */
    @Bean
    public DataSourceTransactionManager dataSourceTransactionManager(DataSource dataSource) {
        return new DataSourceTransactionManager(dataSource);
    }

    /**

     Creates a {@link org.springframework.jdbc.core.JdbcTemplate} object for executing SQL statements against the database.
     @return a {@link org.springframework.jdbc.core.JdbcTemplate} object
     */
    @Bean
    public JdbcTemplate jdbcTemplate() {
        DataSource dataSource = dataSource();
        return new JdbcTemplate(dataSource);
    }
}