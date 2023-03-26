import com.epam.esm.dto.GiftCertificateDTO;
import com.epam.esm.dto.TagDTO;
import com.epam.esm.exception.ResourceNotFoundException;
import com.epam.esm.exception.ServiceException;
import com.epam.esm.mapper.GiftCertificateMapper;
import com.epam.esm.mapper.TagMapper;
import com.epam.esm.model.GiftCertificate;
import com.epam.esm.model.GiftCertificateTag;
import com.epam.esm.model.Tag;
import com.epam.esm.repository.GiftCertificateRepository;
import com.epam.esm.repository.GiftCertificateTagRepository;
import com.epam.esm.repository.TagRepository;
import com.epam.esm.repository.impl.GiftCertificateRepositoryImpl;
import com.epam.esm.repository.impl.GiftCertificateTagRepositoryImpl;
import com.epam.esm.repository.impl.TagRepositoryImpl;
import com.epam.esm.service.GiftCertificateService;

import com.epam.esm.service.impl.GiftCertificateServiceImpl;
import config.TestServiceConfig;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;

@Slf4j
@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {TestServiceConfig.class})
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
        giftCertificateTagRepository = mock(GiftCertificateTagRepositoryImpl.class);
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

    @Test
    public void getByIdTest() throws ResourceNotFoundException {
        long id = 1L;
        GiftCertificate giftCertificate = new GiftCertificate(id, "Gift Certificate 1", "Description 1", 19.20, 30L, "2023-03-15 08:37:40.441373 +00:00", "2023-03-15 08:37:40.441373 +00:00", new HashSet<>());
        GiftCertificateDTO expected = new GiftCertificateDTO(id, "Gift Certificate 1", "Description 1", "19.20", "30", "2023-03-15 08:37:40.441373 +00:00", "2023-03-15 08:37:40.441373 +00:00", new ArrayList<>());

        Mockito.when(certificateRepository.getById(id)).thenReturn(Optional.of(giftCertificate));
        Mockito.when(certificateMapper.toGiftCertificateDTO(giftCertificate)).thenReturn(expected);

        GiftCertificateDTO actual = certificateService.getById(id);
        assertEquals(expected, actual);
    }

    @Test
    public void saveTest() throws ServiceException {
        // given
        GiftCertificateDTO giftCertificateDTO = new GiftCertificateDTO();
        giftCertificateDTO.setName("Gift Certificate 1");
        giftCertificateDTO.setDescription("Description 1");
        giftCertificateDTO.setPrice("10.0");
        giftCertificateDTO.setDuration("20L");

        TagDTO tagDTO1 = new TagDTO();
        tagDTO1.setName("Tag 1");

        TagDTO tagDTO2 = new TagDTO();
        tagDTO2.setName("Tag 2");

        giftCertificateDTO.setTags(Arrays.asList(tagDTO1, tagDTO2));

        GiftCertificate giftCertificate = new GiftCertificate();
        giftCertificate.setName("Gift Certificate 1");
        giftCertificate.setDescription("Description 1");
        giftCertificate.setPrice(10.0);
        giftCertificate.setDuration(20L);

        Tag tag1 = new Tag();
        tag1.setName("Tag 1");

        Tag tag2 = new Tag();
        tag2.setName("Tag 2");

        Mockito.when(certificateMapper.toGiftCertificate(giftCertificateDTO)).thenReturn(giftCertificate);
        Mockito.when(tagMapper.toTag(tagDTO1)).thenReturn(tag1);
        Mockito.when(tagMapper.toTag(tagDTO2)).thenReturn(tag2);
        Mockito.when(certificateRepository.save(giftCertificate)).thenReturn(1L);
        Mockito.when(tagRepository.save(tag1)).thenReturn(1L);
        Mockito.when(tagRepository.save(tag2)).thenReturn(2L);

        // when
        certificateService.save(giftCertificateDTO);

        // then
        Mockito.verify(certificateMapper, Mockito.times(1)).toGiftCertificate(giftCertificateDTO);
        Mockito.verify(tagMapper, Mockito.times(1)).toTag(tagDTO1);
        Mockito.verify(tagMapper, Mockito.times(1)).toTag(tagDTO2);
        Mockito.verify(certificateRepository, Mockito.times(1)).save(giftCertificate);
        Mockito.verify(tagRepository, Mockito.times(1)).save(tag1);
        Mockito.verify(tagRepository, Mockito.times(1)).save(tag2);
        Mockito.verify(giftCertificateTagRepository, Mockito.times(1)).save(new GiftCertificateTag(1L, 1L));
        Mockito.verify(giftCertificateTagRepository, Mockito.times(1)).save(new GiftCertificateTag(1L, 2L));
    }

    @Test
    public void deleteCertificateById_ShouldThrowResourceNotFoundException() throws ResourceNotFoundException {
        long certificateId = 1L;

        doThrow(ResourceNotFoundException.class).when(certificateRepository).delete(certificateId);

        assertThrows(ResourceNotFoundException.class, () -> {
            certificateService.delete(certificateId);
        });
    }

}
