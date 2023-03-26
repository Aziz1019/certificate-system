import com.epam.esm.mapper.GiftCertificateRowMapper;
import com.epam.esm.mapper.TagRowMapper;
import com.epam.esm.model.GiftCertificate;
import com.epam.esm.repository.GiftCertificateRepository;
import com.epam.esm.repository.impl.GiftCertificateRepositoryImpl;
import config.TestRepositoryConfig;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.*;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {TestRepositoryConfig.class})
public class GiftCertificateRepositoryTest {
    @Mock
    private TagRowMapper tagRowMapper;
    @Mock
    private GiftCertificateRowMapper giftCertificateRowMapper;

    @InjectMocks
    private GiftCertificateRepository<GiftCertificate> giftCertificateRepository;

    @BeforeEach
    public void setUp() {
        DataSource dataSource = new EmbeddedDatabaseBuilder()
                .setType(EmbeddedDatabaseType.HSQL)
                .addScript("schema.sql")
                .addScript("data.sql")
                .build();
        JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
        tagRowMapper = mock(TagRowMapper.class);
        giftCertificateRowMapper = mock(GiftCertificateRowMapper.class);
        giftCertificateRepository = new GiftCertificateRepositoryImpl(jdbcTemplate,tagRowMapper,giftCertificateRowMapper);
    }

    @Test
    public void testGetAll() throws SQLException {
        List<GiftCertificate> expectedCertificates = Arrays.asList(
                GiftCertificate.builder()
                        .id(1L)
                        .name("Certificate 1")
                        .description("Description 1")
                        .price(10.00)
                        .duration(30L)
                        .createDate("2022-01-01T00:00:00Z")
                        .lastUpdateDate("2022-01-01T00:00:00Z")
                        .build(),
                GiftCertificate.builder()
                        .id(2L)
                        .name("Certificate 2")
                        .description("Description 2")
                        .price(20.00)
                        .duration(60L)
                        .createDate("2022-01-02T00:00:00Z")
                        .lastUpdateDate("2022-01-02T00:00:00Z")
                        .build()
        );
        when(giftCertificateRowMapper.mapRow(any(ResultSet.class), anyInt()))
                .thenReturn(expectedCertificates.get(0), expectedCertificates.get(1))
                .thenReturn(null);

        List<GiftCertificate> actualCertificates = giftCertificateRepository.getAll();

        assertEquals(expectedCertificates, actualCertificates);
    }

}
