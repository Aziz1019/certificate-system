package com.epam.esm.service.impl;

import com.epam.esm.dto.TagDTO;
import com.epam.esm.service.TagService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TagServiceImpl implements TagService<TagDTO> {
    @Override
    public List<TagDTO> getAll() {
        return null;
    }

    @Override
    public TagDTO getById(long id) {
        return null;
    }

    @Override
    public boolean save(TagDTO tagDTO) {
        return false;
    }

    @Override
    public boolean delete(long id) {
        return false;
    }
}
