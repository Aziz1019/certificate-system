package com.epam.esm.mapper;

import com.epam.esm.dto.TagDTO;
import com.epam.esm.model.Tag;
import org.mapstruct.Mapper;


/**

 Mapper interface that maps Tag and TagDTO.
 */
@Mapper(componentModel = "spring")
public interface TagMapper {
    TagDTO toTagDTO(Tag tag);

    Tag toTag(TagDTO tagDTO);
}
