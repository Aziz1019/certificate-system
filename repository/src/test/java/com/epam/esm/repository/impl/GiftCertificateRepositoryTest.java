package com.epam.esm.repository.impl;

import com.epam.esm.config.TestRepositoryConfig;
import com.epam.esm.model.GiftCertificate;
import com.epam.esm.repository.GiftCertificateRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {TestRepositoryConfig.class})
public class GiftCertificateRepositoryTest {

    @Autowired
    private GiftCertificateRepository giftCertificateRepository;

    @Test
    public void ShouldGetAllSizeEqualFour() {
        assertEquals(4, giftCertificateRepository.getAll().size());
    }

    @Test
    public void GetByIdShouldEqualEmptyWhenNonExistingIdIsProvided() {
        Optional<GiftCertificate> byId = giftCertificateRepository.getById(5);
        Optional<GiftCertificate> expected = Optional.empty();
        assertEquals(expected, byId);
    }

    @Test
    public void ShouldEqualToExpectedGiftCertificateWhenCalledByCorrectId() {
        GiftCertificate expected = new GiftCertificate(
                "name",
                "description",
                0.0,
                0L
        );

        Optional<GiftCertificate> byId = giftCertificateRepository.getById(6);
        if (byId.isPresent()) {
            GiftCertificate certificate = byId.get();
            assertEquals(expected.getName(), certificate.getName());
            assertEquals(expected.getDescription(), certificate.getDescription());
            assertEquals(expected.getPrice(), certificate.getPrice());
            assertEquals(expected.getDuration(), certificate.getDuration());
        } else {
            assertEquals(Optional.empty(), byId);
        }
    }

    @Test
    public void DeleteExistingIdWhenDeleteIsCalled() {

        // Asserts true if the data exists
        Optional<GiftCertificate> existingCertificate = giftCertificateRepository.getById(1);
        assertTrue(existingCertificate.isPresent());

        // Deleting data by id
        giftCertificateRepository.delete(1);
        Optional<GiftCertificate> deletedCertificate = giftCertificateRepository.getById(1);

        // Asserts false if data does not exist
        assertFalse(deletedCertificate.isPresent());
    }


    @Test
    public void ShouldUpdateGiftCertificate() {

        // Getting certificate from a database
        Optional<GiftCertificate> originalCertificate = giftCertificateRepository.getById(1);

        // Updating name of the certificate
        if (originalCertificate.isPresent()) {
            originalCertificate.get().setName("Something Else");

            giftCertificateRepository.update(originalCertificate.get());

            // Calling the same certificate again
            Optional<GiftCertificate> changedCertificate = giftCertificateRepository.getById(1);

            // Checking whether the value has changed
            changedCertificate.ifPresent(giftCertificate -> assertEquals("Something Else", giftCertificate.getName()));

        }
    }


}
