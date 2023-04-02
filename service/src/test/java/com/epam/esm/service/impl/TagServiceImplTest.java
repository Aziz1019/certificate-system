package com.epam.esm.service.impl;

import com.epam.esm.dto.TagDTO;
import com.epam.esm.exception.ResourceNotFoundException;
import com.epam.esm.exception.ServiceException;
import com.epam.esm.mapper.TagMapper;
import com.epam.esm.model.Tag;
import com.epam.esm.repository.GiftCertificateTagRepository;
import com.epam.esm.repository.TagRepository;
import com.epam.esm.service.TagService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.EmptyResultDataAccessException;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
class TagServiceImplTest {
    @Mock
    private GiftCertificateTagRepository giftCertificateTagRepository;
    @Mock
    private TagRepository tagRepository;
    @Mock
    private TagMapper tagMapper;
    @Mock
    TagService tagService;


    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        tagService = new TagServiceImpl(tagRepository, giftCertificateTagRepository, tagMapper);
    }

    @Test
    public void shouldThrowExceptionWhenGetAllTagsIsCalled(){
        doThrow(new DataAccessException("") {}).when(tagRepository).getAll();
        assertThrows(ResourceNotFoundException.class, () -> tagService.getAll());
    }
    @Test
    public void shouldGetAllTagsWithNoExceptions() {
        List<Tag> mockList = new ArrayList<>();
        var tag = new Tag();
        mockList.add(tag);

        List<TagDTO> expectedList = new ArrayList<>();
        var tagDto = new TagDTO();
        expectedList.add(tagDto);

        when(tagRepository.getAll()).thenReturn(mockList);
        when(tagMapper.toTagDTO(tag)).thenReturn(tagDto);

        var actual = tagService.getAll();

        assertEquals(expectedList, actual);
        verify(tagRepository).getAll();
    }

    @Test
    public void shouldThrowExceptionWhenGetByIdTagIsCalled(){
        long id = 1L;
        doThrow(new EmptyResultDataAccessException(1)).when(tagRepository).getById(id);
        assertThrows(ResourceNotFoundException.class, () -> tagService.getById(id));
    }

    @Test
    public void shouldThrowExceptionWhenTagIdIsNotFound(){
        long id = 1L;
        when(tagRepository.getById(1L)).thenReturn(Optional.empty());
        doThrow(ResourceNotFoundException.class).when(tagRepository).getById(id);
        assertThrows(ResourceNotFoundException.class, () -> tagService.getById(id));
    }
    @Test
    public void shouldGetByIdTagWithNoException() {
        long id = 1L;
        var tag = new Tag(id, "Taggy");
        var tagDTO = new TagDTO(id, "Taggy");

        when(tagRepository.getById(id)).thenReturn(Optional.of(tag));
        when(tagMapper.toTagDTO(tag)).thenReturn(tagDTO);

        var actual = tagService.getById(id);
        assertEquals(tagDTO, actual);
        assertDoesNotThrow(() -> tagService.getById(id));

    }

//    @Test
//    public void shouldThrowExceptionWhenSaveTagCalled(){
//        var tag = new Tag();
//        var tagDTO = new TagDTO();
//
//        when(tagMapper.toTagDTO(tag)).thenReturn(tagDTO);
//        doThrow(new DataAccessException(""){}).when(tagRepository).save(tag);
//        assertThrows(ServiceException.class, () -> tagService.save(tagDTO));
//    }
    @Test
    public void shouldSaveTagWithNoException() {
        var tag = new Tag();
        var tagDTO = new TagDTO();

        when(tagMapper.toTag(tagDTO)).thenReturn(tag);
        when(tagRepository.save(tag)).thenReturn(1L);

        assertDoesNotThrow(() -> tagService.save(tagDTO));

    }

    @Test
    public void shouldThrowExceptionWhenDeleteTagIdIsNotFound(){
        long id = 1L;
        doThrow(new EmptyResultDataAccessException(1)).when(tagRepository).delete(id);
        assertThrows(ResourceNotFoundException.class, ()-> tagService.delete(id));
    }

    @Test
    public void shouldThrowExceptionWhenDeleteTagIsCalled(){
        long id = 1L;
        doThrow(new DataAccessException("") {}).when(tagRepository).delete(id);
        assertThrows(ServiceException.class, ()-> tagService.delete(id));
    }

    @Test
    public void shouldDeleteTagWithNoException() {
        var tag = new Tag();
        tag.setId(1L);
        when(tagRepository.getById(tag.getId())).thenReturn(Optional.of(tag));
        when(tagRepository.delete(tag.getId())).thenReturn(true);
        assertDoesNotThrow(() -> tagService.delete(tag.getId()));
    }

}