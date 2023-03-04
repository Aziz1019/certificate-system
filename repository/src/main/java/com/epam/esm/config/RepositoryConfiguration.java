package com.epam.esm.config;

import org.apache.commons.dbcp2.BasicDataSource;
import org.springframework.context.annotation.*;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;

@Configuration
@EnableTransactionManagement
@ComponentScan(basePackages = "com.epam.esm")
@PropertySource("classpath:application.properties")
public class RepositoryConfiguration {
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
    @Bean
    public DataSourceTransactionManager dataSourceTransactionManager(DataSource dataSource){
        return new DataSourceTransactionManager(dataSource);
    }
    @Bean
    public JdbcTemplate jdbcTemplate() {
        DataSource dataSource = dataSource();
        return new JdbcTemplate(dataSource);
    }
}