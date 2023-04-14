package com.epam.esm.mapper;

import com.epam.esm.dto.GiftCertificateDTO;
import com.epam.esm.model.GiftCertificate;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

/**

 Mapper interface that maps GiftCertificate and GiftCertificateDTO.
 */
@org.mapstruct.Mapper(componentModel = "spring")
public interface GiftCertificateMapper {
    GiftCertificateDTO toGiftCertificateDTO(GiftCertificate giftCertificate);


    GiftCertificate toGiftCertificate(GiftCertificateDTO giftCertificateDTO);

}