package com.epam.esm.controller;

import com.epam.esm.config.AbstractTest;
import com.epam.esm.dto.TagDTO;
import com.epam.esm.responseMessage.ResMessage;
import com.epam.esm.service.TagService;
import com.epam.esm.service.impl.TagServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.*;

class TagControllerTest extends AbstractTest {
    @Mock
    private TagController controller;
    @Mock
    private TagService tagService;

    private MockMvc mvc;

    private static final String uri = "/api/tags";

    @BeforeEach
    public void setUp() {
        tagService = mock(TagServiceImpl.class);
        controller = new TagController(tagService);
        mvc = MockMvcBuilders.standaloneSetup(controller).build();
    }

    @Test
    public void testGetTagsReturnsListOfTags() throws Exception {
        // Mocking tagService Get All method
        List<TagDTO> tags = new ArrayList<>();
        TagDTO tag = new TagDTO();
        tags.add(tag);
        when(tagService.getAll()).thenReturn(tags);

        // Checking status and size of the list using mockMvc
        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.get(uri).accept(MediaType.APPLICATION_JSON)).andReturn();

        int status = mvcResult.getResponse().getStatus();
        assertEquals(200, status);

        String content = mvcResult.getResponse().getContentAsString();
        List<TagDTO> tagDTOS = super.mapFromObjectJson(content, TagDTO.class);
        assertEquals(1, tagDTOS.size());
    }

    @Test
    public void testGetTagByIdReturnsMatchingTag() throws Exception {

        // Mocking tagService Get By ID method
        long tagId = 1L;
        TagDTO tagDTO = new TagDTO();
        tagDTO.setId(tagId);
        when(tagService.getById(tagId)).thenReturn(tagDTO);

        // Checking status and tag id using mockMvc
        String uriById = uri + "/" + tagId;
        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.get(uriById)
                        .accept(MediaType.APPLICATION_JSON))
                .andReturn();
        int status = mvcResult.getResponse().getStatus();
        assertEquals(200, status);

        String content = mvcResult.getResponse().getContentAsString();
        TagDTO tagFromJson = super.mapFromJson(content, TagDTO.class);
        assertEquals(tagId, tagFromJson.getId());

    }

    @Test
    public void createTag() throws Exception {
        TagDTO tag = new TagDTO(1, "Taggy");
        doNothing().when(tagService).save(tag);

        String toJson = super.mapToJson(tag);
        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.post(uri)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(toJson))
                .andReturn();

        int status = mvcResult.getResponse().getStatus();
        assertEquals(200, status);

        String content = mvcResult.getResponse().getContentAsString();
        ResMessage<Object> resMessage = super.mapFromJson(content, ResMessage.class);

        assertEquals("Success", resMessage.getMessage());
        assertEquals(HttpStatus.OK, resMessage.getStatusCode());
    }

    @Test
    public void deleteTagById() throws Exception {
        long tagId = 1L;
        TagDTO tag = new TagDTO(tagId, "Taggy");
        doNothing().when(tagService).save(tag);

        String uriForId = uri + "/" + tagId;
        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.delete(uriForId)
                        .accept(MediaType.APPLICATION_JSON))
                .andReturn();

        int status = mvcResult.getResponse().getStatus();
        assertEquals(200, status);

        String content = mvcResult.getResponse().getContentAsString();

        ResMessage<Object> resMessage = super.mapFromJson(content, ResMessage.class);

        assertEquals("Success", resMessage.getMessage());
        assertNull(resMessage.getData());

    }
}