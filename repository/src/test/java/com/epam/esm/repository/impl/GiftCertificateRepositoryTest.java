package com.epam.esm.repository.impl;

import com.epam.esm.model.GiftCertificate;
import com.epam.esm.config.TestRepositoryConfig;
import com.epam.esm.repository.GiftCertificateRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {TestRepositoryConfig.class})
public class GiftCertificateRepositoryTest {

    @Autowired
    private GiftCertificateRepository giftCertificateRepository;

    @Test
    public void theSizeOfGiftCertificateRepoShouldEqualFour() {
        assertEquals(4, giftCertificateRepository.getAll().size());
    }



}
