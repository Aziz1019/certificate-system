package com.epam.esm.mapper;

import com.epam.esm.dto.TagDTO;
import com.epam.esm.model.Tag;
import com.epam.esm.service.impl.GiftCertificateServiceImpl;
import com.epam.esm.service.impl.TagServiceImpl;

@org.mapstruct.Mapper(componentModel = "spring", uses = {GiftCertificateServiceImpl.class, TagServiceImpl.class})
public interface TagMapper {
    TagDTO toTagDTO(Tag tag);
    Tag toTag(TagDTO tagDTO);
}
