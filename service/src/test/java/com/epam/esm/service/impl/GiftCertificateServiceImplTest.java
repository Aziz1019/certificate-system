package com.epam.esm.service.impl;

import com.epam.esm.config.TestServiceConfig;
import com.epam.esm.dto.GiftCertificateDTO;
import com.epam.esm.exception.ResourceNotFoundException;
import com.epam.esm.mapper.GiftCertificateMapper;
import com.epam.esm.mapper.TagMapper;
import com.epam.esm.model.GiftCertificate;
import com.epam.esm.repository.GiftCertificateRepository;
import com.epam.esm.repository.GiftCertificateTagRepository;
import com.epam.esm.repository.TagRepository;
import com.epam.esm.repository.impl.GiftCertificateRepositoryImpl;
import com.epam.esm.repository.impl.GiftCertificateTagRepositoryImpl;
import com.epam.esm.repository.impl.TagRepositoryImpl;
import com.epam.esm.service.GiftCertificateService;
import org.junit.Before;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
class GiftCertificateServiceImplTest {
    @Mock
    private GiftCertificateRepository repository;

    @Mock
    private GiftCertificateMapper certificateMapper;

    @Mock
    private GiftCertificateTagRepository giftCertificateTagRepository;

    @Mock
    private TagRepository tagRepository;

    @Mock
    private TagMapper tagMapper;

    @Mock
    private GiftCertificateService<GiftCertificateDTO> certificateService;


    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        certificateService = new GiftCertificateServiceImpl(repository, tagRepository,giftCertificateTagRepository, certificateMapper, tagMapper);
    }

    @Test
    void getAll() {
        List<GiftCertificate> mockList = new ArrayList<>();
        var giftCertificate = new GiftCertificate();
        mockList.add(giftCertificate);

        when(repository.getAll()).thenReturn(mockList);

        var expected = mockList.stream().map(certificate -> certificateMapper.toGiftCertificateDTO(certificate)).toList();
        var actual = certificateService.getAll();

        assertEquals(expected, actual);
        verify(repository).getAll();
    }


    @Test
    public void getByIdTest() throws ResourceNotFoundException {
        long id = 1L;

        GiftCertificate giftCertificate = new GiftCertificate(id, "Gift Certificate 1", "Description 1", 19.20, 30L, "2023-03-15 08:37:40.441373 +00:00", "2023-03-15 08:37:40.441373 +00:00", new HashSet<>());
        GiftCertificateDTO expected = new GiftCertificateDTO(id, "Gift Certificate 1", "Description 1", "19.20", "30", "2023-03-15 08:37:40.441373 +00:00", "2023-03-15 08:37:40.441373 +00:00", new ArrayList<>());

        when(repository.getById(id)).thenReturn(Optional.of(giftCertificate));
        when(certificateMapper.toGiftCertificateDTO(giftCertificate)).thenReturn(expected);

        GiftCertificateDTO actual = certificateService.getById(id);

        assertEquals(expected, actual);

        verify(repository).getById(id);
    }

    @Test
    public void deleteCertificateById_ShouldThrowResourceNotFoundException() throws ResourceNotFoundException {
        long certificateId = 1L;

        doThrow(ResourceNotFoundException.class).when(repository).delete(certificateId);
        assertThrows(ResourceNotFoundException.class, () -> {
            certificateService.delete(certificateId);
        });
    }

    @Test
    void save() {
    }

    @Test
    void delete() {
    }

    @Test
    void update() {
    }

    @Test
    void getGiftCertificateWithTags() {
    }
}