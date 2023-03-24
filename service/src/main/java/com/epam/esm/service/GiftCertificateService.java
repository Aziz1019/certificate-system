package com.epam.esm.service;

import com.epam.esm.dto.GiftCertificateDTO;
import com.epam.esm.exception.ResourceNotFoundException;

import java.util.List;

/**
 * GiftCertificateService interface defines the methods to handle business logic operations
 * <p>
 * related to gift certificates.
 *
 * @param <T> the type of the gift certificate
 */

public interface GiftCertificateService<T> extends BaseService<T> {
    /**
     * Updates the gift certificate by id.
     *
     * @param t the gift certificate to update
     * @throws ResourceNotFoundException if the gift certificate is not found
     */

    void update(T t) throws ResourceNotFoundException;

    /**
     * Gets a list of gift certificates with matching tags based on the provided criteria.
     *
     * @param name        the name of the gift certificate to search for
     * @param tagName     the name of the tag to search for
     * @param description the description of the gift certificate to search for
     * @param sort        the field to sort the results by
     * @return a list of gift certificates with matching tags
     */

    List<GiftCertificateDTO> getGiftCertificateWithTags(String name, String tagName, String description, String sort);
}
