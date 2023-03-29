//package config;
//
//import com.epam.esm.mapper.GiftCertificateMapper;
//import com.epam.esm.mapper.TagMapper;
//import org.mapstruct.factory.Mappers;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//
//@Configuration
//public class TestServiceConfig {
//    @Testcontainers
//    public class AbstractIntegrationTest {
//        @Container
//        private static final PostgreSQLContainer<?> container = new PostgreSQLContainer<>("postgres:latest");
//
//        static {
//            container.withInitScript("data.sql");
//            container.start();
//            System.setProperty("db.url", container.getJdbcUrl());
//            System.setProperty("db.username", container.getUsername());
//            System.setProperty("db.password", container.getPassword());
//        }
//    }
//    @Bean
//    public GiftCertificateMapper certificateMapper() {
//        return Mappers.getMapper(GiftCertificateMapper.class);
//    }
//    @Bean
//    public TagMapper tagMapper() {
//        return Mappers.getMapper(TagMapper.class);
//    }
//}
