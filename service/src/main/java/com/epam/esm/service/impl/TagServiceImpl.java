package com.epam.esm.service.impl;

import com.epam.esm.dto.TagDTO;
import com.epam.esm.exception.ResourceNotFoundException;
import com.epam.esm.exception.ServiceException;
import com.epam.esm.mapper.TagMapper;
import com.epam.esm.repository.GiftCertificateTagRepository;
import com.epam.esm.repository.TagRepository;
import com.epam.esm.service.TagService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class TagServiceImpl implements TagService {
    private final TagRepository tagRepository;
    private final GiftCertificateTagRepository giftCertificateTagRepository;
    private final TagMapper tagMapper;

    public TagServiceImpl(TagRepository tagRepository, GiftCertificateTagRepository giftCertificateTagRepository, TagMapper tagMapper) {
        this.tagRepository = tagRepository;
        this.giftCertificateTagRepository = giftCertificateTagRepository;
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
            if (tagRepository.getById(id).isPresent()) {
                return tagMapper.toTagDTO(tagRepository.getById(id).get());
            } else throw new ResourceNotFoundException("Tag not found with id " + id);
        } catch (EmptyResultDataAccessException e) {
            log.error("Could not find tag by ID, msg {}", e.getMessage());
            throw new ResourceNotFoundException("Not Found!");
        }
    }

    @Override
    public Long save(TagDTO tagDTO) throws ServiceException {
        try {
            log.info("> > > { Saving tag } ");
            tagRepository.save(tagMapper.toTag(tagDTO));

            return 1L;
        } catch (DataAccessException e) {
            log.error("Could not save TagDTO file, msg {}", e.getMessage());
            throw new ServiceException(e.getMessage(), e);
        }
    }

    @Override
    public void delete(long id) throws ResourceNotFoundException, ServiceException {
        log.info("{ Deleting a tag } ");
        try {
            giftCertificateTagRepository.deleteByTagId(id);
            tagRepository.delete(id);
        } catch (EmptyResultDataAccessException e) {
            log.error("Not able to find, msg {} {}", id, e.getMessage());
            throw new ResourceNotFoundException("Not Found!");
        } catch (DataAccessException ex) {
            log.error("failed to delete certificate with id {}, cause {}", id, ex.getMessage());
            throw new ServiceException("Deleting a tag failed", ex);
        }
    }
}
