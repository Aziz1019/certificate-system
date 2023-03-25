import com.epam.esm.dto.GiftCertificateDTO;
import com.epam.esm.mapper.GiftCertificateMapper;
import com.epam.esm.mapper.TagMapper;
import com.epam.esm.model.GiftCertificate;
import com.epam.esm.model.Tag;
import com.epam.esm.repository.GiftCertificateRepository;
import com.epam.esm.repository.GiftCertificateTagRepository;
import com.epam.esm.repository.TagRepository;
import com.epam.esm.repository.impl.GiftCertificateRepositoryImpl;
import com.epam.esm.repository.impl.TagRepositoryImpl;
import com.epam.esm.service.GiftCertificateService;

import com.epam.esm.service.impl.GiftCertificateServiceImpl;
import config.TestServiceConfiguration;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.HashSet;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;

@Slf4j
@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {TestServiceConfiguration.class})
public class GiftCertificateServiceTest {
    @Mock
    private TagRepository<Tag> tagRepository;
    @Mock
    private GiftCertificateRepository<GiftCertificate> certificateRepository;
    @Mock
    private GiftCertificateMapper certificateMapper;
    @Mock
    private  GiftCertificateTagRepository giftCertificateTagRepository;
    @Mock
    private TagMapper tagMapper;
    @InjectMocks
    private GiftCertificateService<GiftCertificateDTO> certificateService;

    @BeforeEach
    public void setUp() {
        certificateMapper = mock(GiftCertificateMapper.class);
        tagMapper = mock(TagMapper.class);
        tagRepository = mock(TagRepositoryImpl.class);
        certificateRepository = mock(GiftCertificateRepositoryImpl.class);
        certificateService = new GiftCertificateServiceImpl(certificateRepository, tagRepository, giftCertificateTagRepository, certificateMapper, tagMapper);
    }

    @Test
    public void getAllCertificatesTest(){
        Mockito.when(certificateRepository.getAll()).thenReturn(Stream.of(
                new GiftCertificate(1L, "Gift Certificate 1", "Description 1", 19.20, 30L, "2023-03-15 08:37:40.441373 +00:00", "2023-03-15 08:37:40.441373 +00:00", new HashSet<>()),
                new GiftCertificate(2L, "Gift Certificate 2", "Description 2", 20.20, 38L, "2023-03-16 09:37:40.441373 +00:00", "2023-03-18 10:37:40.441373 +00:00", new HashSet<>())).collect(Collectors.toList())
        );

        assertEquals(2, certificateService.getAll().size());
    }

}
