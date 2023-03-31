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
    public void ShouldCheckGetAllSizeEqualFour() {
        // Checking the size of the all gift-certificates
        assertEquals(4, giftCertificateRepository.getAll().size());
    }

    @Test
    public void GetByIdShouldEqualEmptyWhenNonExistingIdIsProvided() {
        // Calling empty id and checking whether it will return Optional.empty
        Optional<GiftCertificate> actual = giftCertificateRepository.getById(5);
        Optional<GiftCertificate> expected = Optional.empty();
        assertEquals(expected, actual);
    }

    @Test
    public void ShouldEqualToExpectedGiftCertificateWhenCalledByCorrectId() {
        // Expected certificate
        GiftCertificate expectedCertificate = new GiftCertificate(
                "name",
                "description",
                0.0,
                0L
        );
        // Calling get by id for the actualCertificate
        Optional<GiftCertificate> actualCertificate = giftCertificateRepository.getById(1);

        if (actualCertificate.isPresent()) {
            GiftCertificate certificate = actualCertificate.get();
            // Checking expecting and actual certificate called by getById
            assertEquals(expectedCertificate.getName(), certificate.getName());
            assertEquals(expectedCertificate.getDescription(), certificate.getDescription());
            assertEquals(expectedCertificate.getPrice(), certificate.getPrice());
            assertEquals(expectedCertificate.getDuration(), certificate.getDuration());
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
