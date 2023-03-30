package com.epam.esm.config;

import com.epam.esm.mapper.GiftCertificateRowMapper;
import com.epam.esm.mapper.TagRowMapper;
import com.epam.esm.model.GiftCertificate;
import com.epam.esm.model.Tag;
import com.epam.esm.repository.GiftCertificateRepository;
import com.epam.esm.repository.TagRepository;
import com.epam.esm.repository.impl.GiftCertificateRepositoryImpl;
import com.epam.esm.repository.impl.TagRepositoryImpl;
import org.apache.commons.dbcp2.BasicDataSource;
import org.h2.jdbcx.JdbcDataSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabase;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;
import org.springframework.jdbc.datasource.init.DataSourceInitializer;
import org.springframework.jdbc.datasource.init.ResourceDatabasePopulator;

import javax.sql.DataSource;

@Configuration
@ComponentScan(lazyInit = true)
public class TestRepositoryConfig {
    @Bean
    public DataSource dataSource() {
        // An embedded H2 database instance
        EmbeddedDatabaseBuilder builder = new EmbeddedDatabaseBuilder();
        EmbeddedDatabase db = builder.setType(EmbeddedDatabaseType.H2)
                .addScript("classpath:data.sql")
                .build();

        // A Spring DataSource that wraps the H2 database instance
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName("org.postgresql.Driver");
        dataSource.setUrl("jdbc:h2:mem:testdb;MODE=PostgreSQL");
        dataSource.setUsername("sa");
        dataSource.setPassword("");
        dataSource.setSchema("PUBLIC");
        return dataSource;
    }

    @Bean
    public JdbcTemplate jdbcTemplate() {
        return new JdbcTemplate(dataSource());
    }

}
