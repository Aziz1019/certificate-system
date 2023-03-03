package com.epam.esm.mapper;

import com.epam.esm.dto.GiftCertificateDTO;
import com.epam.esm.model.GiftCertificate;
import com.epam.esm.service.impl.GiftCertificateServiceImpl;

@org.mapstruct.Mapper(componentModel = "spring", uses = {GiftCertificateServiceImpl.class})
public interface GiftCertificateMapper {
    GiftCertificateDTO toGiftCertificateDTO(GiftCertificate giftCertificate);
    GiftCertificate toGiftCertificate(GiftCertificateDTO giftCertificateDTO);

}