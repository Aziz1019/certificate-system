//import com.epam.esm.enums.TableQueries;
//import com.epam.esm.mapper.GiftCertificateRowMapper;
//import com.epam.esm.model.GiftCertificate;
//import com.epam.esm.repository.GiftCertificateRepository;
//import org.junit.Assert;
//import org.junit.Before;
//import org.junit.Test;
//import org.junit.runner.RunWith;
//import org.mockito.Mock;
//import org.mockito.Mockito;
//import org.mockito.MockitoAnnotations;
//import org.mockito.junit.MockitoJUnitRunner;
//import org.springframework.jdbc.core.JdbcTemplate;
//
//import java.util.ArrayList;
//import java.util.HashSet;
//import java.util.List;
//
//@RunWith(MockitoJUnitRunner.class)
//public class GiftCertificateRepositoryTest {
//
//    @Mock
//    private JdbcTemplate jdbcTemplate;
//
//    @Mock
//    private GiftCertificateRepository<GiftCertificate> giftCertificateRepository;
//
//    @Mock
//    private GiftCertificateRowMapper giftCertificateRowMapper;
//
//    @Before
//    public void setUp() {
//        MockitoAnnotations.openMocks(this);
//    }
//
//    @Test
//    public void testGetAll() {
//        List<GiftCertificate> expectedGiftCertificates = new ArrayList<>();
//        expectedGiftCertificates.add(new GiftCertificate(1L, "Gift Certificate 1", "Description 1", 19.20, 30L, "2023-03-15 08:37:40.441373 +00:00", "2023-03-15 08:37:40.441373 +00:00", new HashSet<>()));
//        expectedGiftCertificates.add(new GiftCertificate(2L, "Gift Certificate 2", "Description 2", 20.00, 60L, "2023-03-16 09:37:40.441373 +00:00", "2023-03-16 09:37:40.441373 +00:00", new HashSet<>()));
//
//        Mockito.when(jdbcTemplate.query(TableQueries.GET_ALL_GIFT_CERTIFICATES.getQuery(), giftCertificateRowMapper)).thenReturn(expectedGiftCertificates);
//
//        List<GiftCertificate> actualGiftCertificates = giftCertificateRepository.getAll();
//
//        Mockito.verify(jdbcTemplate, Mockito.times(1)).query(TableQueries.GET_ALL_GIFT_CERTIFICATES.getQuery(), giftCertificateRowMapper);
//
//        Assert.assertEquals(expectedGiftCertificates.size(), actualGiftCertificates.size());
//        Assert.assertEquals(expectedGiftCertificates.get(0).getName(), actualGiftCertificates.get(0).getName());
//        Assert.assertEquals(expectedGiftCertificates.get(1).getDescription(), actualGiftCertificates.get(1).getDescription());
//        // Assert other properties
//    }
//}
//
//
//
//
