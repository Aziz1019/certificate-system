package com.epam.esm.controller;

import com.epam.esm.config.AbstractTest;
import com.epam.esm.dto.GiftCertificateDTO;
import com.epam.esm.responseMessage.ResMessage;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class GiftCertificateControllerTest extends AbstractTest {

    @Autowired
    private MockMvc mvc;

    @Test
    public void testGetCertificatesReturnsAllCertificates() throws Exception {
        String uri = "/api/certificates";
        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.get(uri)
                .accept(MediaType.APPLICATION_JSON_VALUE)).andReturn();

        int status = mvcResult.getResponse().getStatus();
        assertEquals(200, status);
        String content = mvcResult.getResponse().getContentAsString();
        List<GiftCertificateDTO> certificateList = super.mapFromObjectJson(content);
        assertTrue(certificateList.size() > 0);
    }

    @Test
    public void testGetCertificateByIdReturnsMatchingCertificate() throws Exception {
        Long certificateId = 1L; // Set the certificate ID to fetch

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
        String uri = "/api/certificates";
        GiftCertificateDTO giftCertificateDTO = new GiftCertificateDTO();
        giftCertificateDTO.setName("Test Gift");
        giftCertificateDTO.setDescription("Test Description");
        giftCertificateDTO.setPrice("10.99");
        giftCertificateDTO.setDuration("30");

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
        String uri = "/api/certificates";
        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.get(uri)
                .accept(MediaType.APPLICATION_JSON_VALUE)).andReturn();

        int status = mvcResult.getResponse().getStatus();
        assertEquals(200, status);
        String content = mvcResult.getResponse().getContentAsString();
        List<GiftCertificateDTO> certificateList = super.mapFromObjectJson(content);
        assertTrue(certificateList.size() > 0);

        // Choose one of the certificates to update
        GiftCertificateDTO certificateToUpdate = certificateList.get(0);
        certificateToUpdate.setName("Updated name");

        // Perform the update request
        MvcResult updateMvcResult = mvc.perform(MockMvcRequestBuilders.patch(uri)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(super.mapToJson(certificateToUpdate)))
                .andReturn();

        int updateStatus = updateMvcResult.getResponse().getStatus();
        assertEquals(200, updateStatus);

        // Verify that the certificate was updated
        String updateUri = uri + "/" + certificateToUpdate.getId();
        MvcResult getMvcResult = mvc.perform(MockMvcRequestBuilders.get(updateUri)
                .accept(MediaType.APPLICATION_JSON_VALUE)).andReturn();

        int getStatus = getMvcResult.getResponse().getStatus();
        assertEquals(200, getStatus);

        String getContent = getMvcResult.getResponse().getContentAsString();
        GiftCertificateDTO updatedCertificate = super.mapFromJson(getContent, GiftCertificateDTO.class);

        assertEquals("Updated name", updatedCertificate.getName());
    }

    @Test
    public void testDeleteByIdReturnsSuccessIfDeleted() throws Exception {
        long certificateId = 1L; // Set the certificate ID to fetch

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

    @Test
    void testGetGiftCertificatesWithTagsReturnsCertificateWithDescriptionTaggy() throws Exception {
        String uri = "/api/certificates";
        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.put(uri)
                        .param("description", "Taggy")
                        .accept(MediaType.APPLICATION_JSON_VALUE))
                .andReturn();
        int status = mvcResult.getResponse().getStatus();
        assertEquals(200, status);
        String content = mvcResult.getResponse().getContentAsString();
        List<GiftCertificateDTO> giftCertificateDTOS = super.mapFromObjectJson(content);
        assertEquals(1, giftCertificateDTOS.size());
    }
}