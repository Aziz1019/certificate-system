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
import org.springframework.jdbc.datasource.init.DataSourceInitializer;
import org.springframework.jdbc.datasource.init.ResourceDatabasePopulator;

import javax.sql.DataSource;

@Configuration
@ComponentScan(lazyInit = true)
public class TestRepositoryConfig {

    @Bean
    public DataSource dataSource() {
        JdbcDataSource dataSource = new JdbcDataSource();
        dataSource.setURL("jdbc:h2:mem:test;MODE=PostgreSQL;DATABASE_TO_LOWER=TRUE;DEFAULT_NULL_ORDERING=HIGH");
        dataSource.setUser("postgres");
        dataSource.setPassword("root123");
        return dataSource;
    }

    @Bean
    public JdbcTemplate jdbcTemplate() {
        return new JdbcTemplate(dataSource());
    }

    @Bean
    public DataSourceInitializer dataSourceInitializer(DataSource dataSource) {
        ResourceDatabasePopulator populator = new ResourceDatabasePopulator();
        populator.addScript(new ClassPathResource("data.sql"));

        DataSourceInitializer initializer = new DataSourceInitializer();
        initializer.setDataSource(dataSource);
        initializer.setDatabasePopulator(populator);

        return initializer;
    }
//
//    @Bean
//    public GiftCertificateRowMapper giftCertificateRowMapper() {
//        return new GiftCertificateRowMapper();
//    }
//
//    @Bean
//    public TagRowMapper tagRowMapper() {
//        return new TagRowMapper();
//    }
//
//    @Bean
//    public TagRepository<Tag> tagRepository(JdbcTemplate jdbcTemplate,
//                                            TagRowMapper tagRowMapper) {
//        return new TagRepositoryImpl(
//                jdbcTemplate,
//                tagRowMapper);
//    }
//
//    @Bean
//    public GiftCertificateRepository<GiftCertificate> certificateRepository(JdbcTemplate jdbcTemplate,
//                                                                            TagRowMapper tagRowMapper,
//                                                                            GiftCertificateRowMapper certificateRowMapper) {
//        return new GiftCertificateRepositoryImpl(
//                jdbcTemplate,
//                tagRowMapper,
//                certificateRowMapper);
//    }

}
