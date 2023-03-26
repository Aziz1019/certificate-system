package config;

import com.epam.esm.mapper.GiftCertificateMapper;
import com.epam.esm.mapper.TagMapper;
import org.mapstruct.factory.Mappers;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class TestServiceConfig {
    @Bean
    public GiftCertificateMapper certificateMapper() {
        return Mappers.getMapper(GiftCertificateMapper.class);
    }
    @Bean
    public TagMapper tagMapper() {
        return Mappers.getMapper(TagMapper.class);
    }
}
