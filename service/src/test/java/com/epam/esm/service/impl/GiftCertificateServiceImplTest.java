package com.epam.esm.service.impl;

import com.epam.esm.dto.GiftCertificateDTO;
import com.epam.esm.exception.ResourceNotFoundException;
import com.epam.esm.exception.ServiceException;
import com.epam.esm.mapper.GiftCertificateMapper;
import com.epam.esm.mapper.TagMapper;
import com.epam.esm.model.GiftCertificate;
import com.epam.esm.repository.GiftCertificateRepository;
import com.epam.esm.repository.GiftCertificateTagRepository;
import com.epam.esm.repository.TagRepository;
import com.epam.esm.service.GiftCertificateService;
import com.epam.esm.service.TagService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.EmptyResultDataAccessException;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
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
    TagService tagService;

    @Mock
    private TagMapper tagMapper;

    @Mock
    private GiftCertificateService certificateService;


    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        certificateService = new GiftCertificateServiceImpl(repository, tagRepository, giftCertificateTagRepository, certificateMapper, tagMapper);
        tagService = new TagServiceImpl(tagRepository, giftCertificateTagRepository, tagMapper);
    }

    @Test
    public void shouldThrowResourceNotFoundExceptionWhenGetAllMethodIsCalled() {
        doThrow(new DataAccessException("") {
        }).when(repository).getAll();
        assertThrows(ResourceNotFoundException.class, () -> certificateService.getAll());
    }

    @Test
    public void shouldGetAllGiftCertificatesWithNoExceptions() {
        List<GiftCertificate> mockList = new ArrayList<>();
        var giftCertificate = new GiftCertificate();
        mockList.add(giftCertificate);

        List<GiftCertificateDTO> mockDTOList = new ArrayList<>();
        var gifCertificateDTO = new GiftCertificateDTO();
        mockDTOList.add(gifCertificateDTO);

        when(repository.getAll()).thenReturn(mockList);
        when(certificateMapper.toGiftCertificateDTO(giftCertificate)).thenReturn(gifCertificateDTO);

        var actual = certificateService.getAll();

        assertEquals(mockDTOList, actual);
        assertDoesNotThrow(() -> certificateService.getAll());
    }


    @Test
    public void shouldThrowResourceNotFoundExceptionWhenGetByIdIsCalled() {
        doThrow(ResourceNotFoundException.class).when(repository).getById(1L);
        assertThrows(ResourceNotFoundException.class, () -> certificateService.getById(1L));
    }

    @Test
    public void shouldGetCertificateByIdWithNoExceptions() throws ResourceNotFoundException {
        long id = 1L;

        var giftCertificate = new GiftCertificate(id, "Gift Certificate 1", "Description 1", 19.20, 30L, "2023-03-15 08:37:40.441373 +00:00", "2023-03-15 08:37:40.441373 +00:00", new HashSet<>());
        var expected = new GiftCertificateDTO(id, "Gift Certificate 1", "Description 1", "19.20", "30", "2023-03-15 08:37:40.441373 +00:00", "2023-03-15 08:37:40.441373 +00:00", new ArrayList<>());

        when(repository.getById(id)).thenReturn(Optional.of(giftCertificate));
        when(certificateMapper.toGiftCertificateDTO(giftCertificate)).thenReturn(expected);
        var actual = certificateService.getById(id);

        assertEquals(expected, actual);
        assertDoesNotThrow(() -> certificateService.getById(id));
    }


    @Test
    public void shouldThrowServiceExceptionWhenSaveIsCalled() {
        doThrow(new EmptyResultDataAccessException(1)).when(repository).save(null);
        assertThrows(ServiceException.class, () -> certificateService.save(null));
    }


    @Test
    public void shouldNotThrowExceptionAndSaveGiftCertificateWhenGetTagsIsNull() {
        var certificate = new GiftCertificate();
        var giftCertificateDTO = new GiftCertificateDTO();

        when(giftCertificateDTO.getTags()).thenReturn(null);
        when(certificateMapper.toGiftCertificate(giftCertificateDTO)).thenReturn(certificate);
        when(repository.save(certificate)).thenReturn(1L);

        assertDoesNotThrow(() -> certificateService.save(giftCertificateDTO));
    }


    @Test
    public void shouldThrowExceptionIfIdIsNotFoundWhenUpdatingCertificate() {

        long id = 1L;
        GiftCertificate certificate = new GiftCertificate(id, "Gift Certificate 1", "Description 1", 19.20, 30L, "2023-03-15 08:37:40.441373 +00:00", "2023-03-15 08:37:40.441373 +00:00", new HashSet<>());
        GiftCertificateDTO giftCertificateDTO = new GiftCertificateDTO(id, "Gift Certificate 1", "Description 1", "19.20", "30", "2023-03-15 08:37:40.441373 +00:00", "2023-03-15 08:37:40.441373 +00:00", new ArrayList<>());

        when(repository.getById(id)).thenReturn(Optional.of(certificate));

        when(certificateMapper.toGiftCertificate(giftCertificateDTO)).thenReturn(certificate);

        doThrow(new EmptyResultDataAccessException(1)).when(repository).update(certificate);

        assertThrows(ResourceNotFoundException.class, () -> certificateService.update(giftCertificateDTO));

    }

    @Test
    public void shouldUpdateCertificateWithNoExceptions() {
        Optional<GiftCertificate> optionalGiftCertificate = repository.getById(1);
        GiftCertificateDTO giftCertificateDTO = new GiftCertificateDTO();

        if (optionalGiftCertificate.isPresent()) {
            GiftCertificate certificate = optionalGiftCertificate.get();

            doNothing().when(repository).update(certificate);
            doNothing().when(repository).tagSetter(certificate);
            when(certificateMapper.toGiftCertificateDTO(certificate)).thenReturn(giftCertificateDTO);

            assertDoesNotThrow(() -> certificateService.update(giftCertificateDTO));
        }
    }


    @Test
    public void deleteCertificateByIdShouldThrowResourceNotFoundException() throws ResourceNotFoundException {

        long certificateId = 1L;
        doThrow(EmptyResultDataAccessException.class).when(repository).getById(certificateId);
        assertThrows(ResourceNotFoundException.class, () -> certificateService.delete(certificateId));
    }

    @Test
    public void shouldDeleteCertificateByIdWithNoExceptions() {

        GiftCertificate certificate = new GiftCertificate();
        certificate.setId(1L);

        when(repository.getById(certificate.getId())).thenReturn(Optional.of(certificate));

        when(repository.delete(certificate.getId())).thenReturn(true);

        assertDoesNotThrow(() -> certificateService.delete(certificate.getId()));
    }


    @Test
    public void shouldThrowExceptionWhenGiftCertificateWithTagsIsCalled() {
        doThrow(new DataAccessException(" ") {
        }).when(repository).getGiftCertificateWithTags(null, null, null, null);
        Map<String, String> allParams = new HashMap<>();
        assertThrows(ServiceException.class, () -> certificateService.getGiftCertificateWithTags(allParams));
    }

    @Test
    public void shouldGetGiftCertificateWithTagsWithNoExceptions() {

        String name = "Gift Certificate 1";
        String description = "Description 1";

        List<GiftCertificate> certificates = new ArrayList<>();
        GiftCertificate certificate = new GiftCertificate(1L, "Gift Certificate 1", "Description 1", 19.20, 30L, "2023-03-15 08:37:40.441373 +00:00", "2023-03-15 08:37:40.441373 +00:00", new HashSet<>());
        certificates.add(certificate);

        GiftCertificateDTO giftCertificateDTO = new GiftCertificateDTO(1L, "Gift Certificate 1", "Description 1", "19.20", "30", "2023-03-15 08:37:40.441373 +00:00", "2023-03-15 08:37:40.441373 +00:00", new ArrayList<>());

        when(repository.getGiftCertificateWithTags(name, "", description, "")).thenReturn(certificates);
        when(certificateMapper.toGiftCertificateDTO(certificate)).thenReturn(giftCertificateDTO);
        Map<String, String> allParams = new HashMap<>();
        allParams.put("name",name);
        allParams.put("tag","");
        allParams.put("description", description);
        allParams.put("sort", "");

        assertDoesNotThrow(() -> certificateService.getGiftCertificateWithTags(allParams));
    }

}