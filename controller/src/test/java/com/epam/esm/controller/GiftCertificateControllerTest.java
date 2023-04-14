package com.epam.esm.controller;

import com.epam.esm.config.AbstractTest;
import com.epam.esm.dto.GiftCertificateDTO;
import com.epam.esm.responseMessage.ResMessage;
import com.epam.esm.service.GiftCertificateService;
import com.epam.esm.service.impl.GiftCertificateServiceImpl;
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

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;


class GiftCertificateControllerTest extends AbstractTest {

    @Mock
    private GiftCertificateController controller;
    @Mock
    private GiftCertificateService giftCertificateService;

    private MockMvc mvc;

    @BeforeEach
    public void setUp() {
        giftCertificateService = mock(GiftCertificateServiceImpl.class);
        controller = new GiftCertificateController(giftCertificateService);
        mvc = MockMvcBuilders.standaloneSetup(controller).build();
    }


    @Test
    public void testGetCertificatesReturnsAllCertificates() throws Exception {
        // Mocking giftCertificateService Get All method
        List<GiftCertificateDTO> certificates = new ArrayList<>();
        certificates.add(new GiftCertificateDTO());
        when(giftCertificateService.getAll()).thenReturn(certificates);

        String uri = "/api/certificates";
        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.get(uri)
                .accept(MediaType.APPLICATION_JSON_VALUE)).andReturn();

        int status = mvcResult.getResponse().getStatus();
        assertEquals(200, status);

        String content = mvcResult.getResponse().getContentAsString();
        List<GiftCertificateDTO> certificateList = super.mapFromObjectJson(content, GiftCertificateDTO.class);
        assertEquals(1, certificateList.size());
    }

    @Test
    public void testGetCertificateByIdReturnsMatchingCertificate() throws Exception {
        // Mocking giftCertificateService Get By ID method
        long certificateId = 1L;
        GiftCertificateDTO certificateDTO = new GiftCertificateDTO();
        certificateDTO.setId(1L);
        when(giftCertificateService.getById(certificateId)).thenReturn(certificateDTO);

        String uri = "/api/certificates/" + certificateId;
        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.get(uri)
                .accept(MediaType.APPLICATION_JSON_VALUE)).andReturn();

        int status = mvcResult.getResponse().getStatus();
        assertEquals(200, status);
        String content = mvcResult.getResponse().getContentAsString();
        GiftCertificateDTO certificate = super.mapFromJson(content, GiftCertificateDTO.class);
        assertNotNull(certificate);
        assertEquals(certificateId, certificate.getId());
    }

    @Test
    public void testCreateGiftCertificateReturnsNewGiftCertificate() throws Exception {
        // Mocking giftCertificateService Save method
        String uri = "/api/certificates";
        GiftCertificateDTO giftCertificateDTO = new GiftCertificateDTO();
        giftCertificateDTO.setName("Test Gift");
        giftCertificateDTO.setDescription("Test Description");
        giftCertificateDTO.setPrice("10.99");
        giftCertificateDTO.setDuration("30");

        doNothing().when(giftCertificateService).save(giftCertificateDTO);

        String inputJson = super.mapToJson(giftCertificateDTO);
        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.post(uri)
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(inputJson))
                .andReturn();

        int status = mvcResult.getResponse().getStatus();
        assertEquals(200, status);

        String content = mvcResult.getResponse().getContentAsString();
        ResMessage<Object> resMessage = super.mapFromJson(content, ResMessage.class);

        assertEquals(HttpStatus.OK, resMessage.getStatusCode());
        assertEquals("Success", resMessage.getMessage());
    }

    @Test
    public void testUpdateGiftCertificateReturnsUpdatedNameOfChosenCertificate() throws Exception {
        // Mocking giftCertificateService update method
        long certificateId = 1L;
        GiftCertificateDTO giftCertificateDTO = new GiftCertificateDTO();
        giftCertificateDTO.setId(certificateId);
        when(giftCertificateService.getById(certificateId)).thenReturn(giftCertificateDTO);

        // Sending getById request to specific id (for checking purposes)
        String uri = "/api/certificates";
        String uri_for_id = uri + "/" + certificateId;
        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.get(uri_for_id)
                .accept(MediaType.APPLICATION_JSON_VALUE)).andReturn();

        int status = mvcResult.getResponse().getStatus();
        assertEquals(200, status);

        String content = mvcResult.getResponse().getContentAsString();
        // Updating the name of the GiftCertificate
        GiftCertificateDTO certificateToUpdate = super.mapFromJson(content, GiftCertificateDTO.class);
        certificateToUpdate.setName("Updated name");

        // Perform the update request
        doNothing().when(giftCertificateService).update(certificateToUpdate);

        MvcResult updateMvcResult = mvc.perform(MockMvcRequestBuilders.patch(uri)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(super.mapToJson(certificateToUpdate)))
                .andReturn();

        int updateStatus = updateMvcResult.getResponse().getStatus();
        assertEquals(200, updateStatus);

    }

    @Test
    public void testDeleteByIdReturnsSuccessIfDeleted() throws Exception {
        long certificateId = 1L; // Set the certificate ID to fetch

        doNothing().when(giftCertificateService).delete(certificateId);

        String uri = "/api/certificates/" + certificateId;
        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.delete(uri)
                .accept(MediaType.APPLICATION_JSON_VALUE)).andReturn();

        int status = mvcResult.getResponse().getStatus();
        assertEquals(200, status);

        String content = mvcResult.getResponse().getContentAsString();

        ResMessage<Object> resMessage = super.mapFromJson(content, ResMessage.class);

        assertEquals("Success", resMessage.getMessage());
        assertNull(resMessage.getData());

    }
}