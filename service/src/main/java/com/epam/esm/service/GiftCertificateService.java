package com.epam.esm.service;

import com.epam.esm.dto.GiftCertificateDTO;
import com.epam.esm.dto.TagDTO;
import com.epam.esm.exception.ResourceNotFoundException;

import java.util.List;

public interface GiftCertificateService<T> extends BaseService<T>{
    void update(T t) throws ResourceNotFoundException;
    List<GiftCertificateDTO> getByTag(TagDTO tag) throws ResourceNotFoundException;
    List<GiftCertificateDTO> getGiftCertificateWithTags(String name, String description, String sort);
}
