package com.epam.esm.service.impl;

import com.epam.esm.dto.TagDTO;
import com.epam.esm.exception.ResourceNotFoundException;
import com.epam.esm.exception.ServiceException;
import com.epam.esm.mapper.TagMapper;
import com.epam.esm.model.Tag;
import com.epam.esm.repository.TagRepository;
import com.epam.esm.service.TagService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class TagServiceImpl implements TagService<TagDTO> {
    private final TagRepository<Tag> tagRepository;
    private final TagMapper tagMapper;

    public TagServiceImpl(TagRepository<Tag> tagRepository, TagMapper tagMapper) {
        this.tagRepository = tagRepository;
        this.tagMapper = tagMapper;
    }

    @Override
    public List<TagDTO> getAll() throws ResourceNotFoundException {
        log.info("> > > { Get All Tags } ");
        try {
            return tagRepository.getAll().stream().map(tagMapper::toTagDTO).toList();
        } catch (DataAccessException e) {
            log.error("Could not find tags, msg {}", e.getMessage());
            throw new ResourceNotFoundException("Not Found");
        }
    }

    @Override
    public TagDTO getById(long id) throws ResourceNotFoundException {
        try {
            log.info("> > > { Get By ID } ");
            return tagMapper.toTagDTO(tagRepository.getById(id).get());
        } catch (EmptyResultDataAccessException e) {
            log.error("Could not find tag by ID, msg {}", e.getMessage());
            throw new ResourceNotFoundException("Not Found!");
        }
    }

    @Override
    public boolean save(TagDTO tagDTO) throws ServiceException {
        try {
            log.info("> > > { Saving tag } ");
            return tagRepository.save(tagMapper.toTag(tagDTO));
        } catch (DataAccessException e) {
            log.error("Could not save TagDTO file, msg {}", e.getMessage());
            throw new ServiceException("Could not save TagDTO", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public boolean delete(long id) throws ResourceNotFoundException, ServiceException {
        log.info("{ Deleting a tag } ");
        try {
            tagRepository.getById(id);
            tagRepository.delete(id);
            return true;
        } catch (EmptyResultDataAccessException e) {
            log.error("Not able to find, msg {} {}", id, e.getMessage());
            throw new ResourceNotFoundException("Not Found!");
        } catch (DataAccessException ex) {
            log.error("failed to delete certificate with id {}, cause {}", id, ex.getMessage());
            throw new ServiceException("Deleting a tag failed", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
