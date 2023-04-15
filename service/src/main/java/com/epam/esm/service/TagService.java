package com.epam.esm.service;

import com.epam.esm.dto.TagDTO;
import com.epam.esm.exception.ServiceException;

public interface TagService extends BaseService<TagDTO> {
    Long save(TagDTO tagDTO) throws ServiceException;
}
