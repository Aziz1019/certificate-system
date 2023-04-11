package com.epam.esm.service;

import com.epam.esm.dto.GiftCertificateDTO;
import com.epam.esm.exception.ResourceNotFoundException;
import com.epam.esm.exception.ServiceException;

import java.util.List;
import java.util.Map;

/**
 * GiftCertificateService interface defines the methods to handle business logic operations
 * <p>
 * related to gift certificates.
 *
 * @param <T> the type of the gift certificate
 */

public interface GiftCertificateService extends BaseService<GiftCertificateDTO> {
    /**
     * Updates the gift certificate by id.
     *
     * @param t the gift certificate to update
     * @throws ResourceNotFoundException if the gift certificate is not found
     */

    void update(GiftCertificateDTO t) throws ResourceNotFoundException;

    /**
     * Gets a list of gift certificates with matching tags based on the provided criteria.
     *
     */

    List<GiftCertificateDTO> getGiftCertificateWithTags(Map<String, String> allParams) throws ServiceException;
}
