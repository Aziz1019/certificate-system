package com.epam.esm.service.impl;

import com.epam.esm.dto.GiftCertificateDTO;
import com.epam.esm.dto.GiftCertificateSaveDTO;
import com.epam.esm.exception.ResourceNotFoundException;
import com.epam.esm.exception.ServiceException;
import com.epam.esm.mapper.GiftCertificateMapper;
import com.epam.esm.mapper.TagMapper;
import com.epam.esm.model.GiftCertificate;
import com.epam.esm.model.GiftCertificateTag;
import com.epam.esm.model.Tag;
import com.epam.esm.repository.GiftCertificateRepository;
import com.epam.esm.repository.GiftCertificateTagRepository;
import com.epam.esm.repository.TagRepository;
import com.epam.esm.service.GiftCertificateService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Transactional
@Service
@Slf4j
public class GiftCertificateServiceImpl implements GiftCertificateService {
    private final GiftCertificateRepository giftCertificateRepository;
    private final TagRepository tagRepository;
    private final GiftCertificateTagRepository giftCertificateTagRepository;
    private final GiftCertificateMapper certificateMapper;
    private final TagMapper tagMapper;


    public GiftCertificateServiceImpl(GiftCertificateRepository giftCertificateRepository, TagRepository tagRepository, GiftCertificateTagRepository giftCertificateTagRepository, GiftCertificateMapper certificateMapper, TagMapper tagMapper) {
        this.giftCertificateRepository = giftCertificateRepository;
        this.tagRepository = tagRepository;
        this.giftCertificateTagRepository = giftCertificateTagRepository;
        this.certificateMapper = certificateMapper;
        this.tagMapper = tagMapper;
    }

    /**
     * Retrieves all gift certificates from the repository and maps them to GiftCertificateDTO objects.
     *
     * @return a list of GiftCertificateDTO objects representing all gift certificates in the repository
     * @throws ResourceNotFoundException if an error occurs while accessing the repository or if no gift certificates are found
     */

    @Override
    public List<GiftCertificateDTO> getAll() throws ResourceNotFoundException {
        try {
            log.info("> > >  Loading Certificates . . .   < < <");
            return giftCertificateRepository.getAll()
                    .stream()
                    .map(certificateMapper::toGiftCertificateDTO)
                    .collect(Collectors.toList());
        } catch (DataAccessException e) {
            log.error("> > > Problem occurred while getting certificates -> {}   < < <", e.getMessage());
            throw new ResourceNotFoundException("Gift Certificates were not found!", e);
        }
    }

    @Override
    public GiftCertificateDTO getById(long id) throws ResourceNotFoundException {
        log.info("> > > {Get Gift Certificate By ID}");
        Optional<GiftCertificate> byId = giftCertificateRepository.getById(id);
        return byId.map(certificateMapper::toGiftCertificateDTO).orElseThrow(
                () -> {
                    String errorMessage = "Resource not found for ID: " + id;
                    log.error(errorMessage);
                    throw new ResourceNotFoundException(errorMessage);
                });
    }

    @Override
    public Long save(GiftCertificateSaveDTO giftCertificateSaveDTO) throws ServiceException {
        log.info("> > > { Add New Gift Certificate }");
        try {
            return giftCertificateRepository.save(certificateMapper.toGiftCertificate(giftCertificateSaveDTO));

        } catch (EmptyResultDataAccessException e) {
            log.error("Could not create gift certificate - > {}", e.getMessage());
            throw new ServiceException("Error saving resource", e);
        }
    }

    @Override
    public void delete(long id) throws ResourceNotFoundException {
        try {
            log.info("{ Deleting Gift Certificate }");
            giftCertificateRepository.getById(id);
            giftCertificateTagRepository.delete(id);
            giftCertificateRepository.delete(id);

        } catch (EmptyResultDataAccessException ex) {
            log.error("cannot delete gift certificate by id {}, msg {}", id, ex.getMessage());
            throw new ResourceNotFoundException("Certificate could not be deleted! ", ex);
        }
    }

    @Override
    public GiftCertificateDTO update(GiftCertificateDTO giftCertificateDTO) throws ResourceNotFoundException {
        long id = giftCertificateDTO.getId();
        try {
            log.info("> > > { Updating a certificate } < < <");

            giftCertificateRepository.update(certificateMapper.toGiftCertificate(giftCertificateDTO));

            return getById(id);
        } catch (EmptyResultDataAccessException ex) {
            log.error("failed to update certificate with id {}, cause {}", id, ex.getMessage());
            throw new ResourceNotFoundException("could not update certificate ", ex);
        }
    }

    @Override
    public List<GiftCertificateDTO> getGiftCertificateWithTags(Map<String, String> allParams) throws ServiceException {
        try {
            String name = allParams.get("name");
            String description = allParams.get("description");
            String tagName = allParams.get("tag");
            String sort = allParams.getOrDefault("sort", "name_asc");

            List<GiftCertificate> giftCertificateWithTags = giftCertificateRepository.getGiftCertificateWithTags(name, tagName, description, sort);
            giftCertificateWithTags.forEach(giftCertificateRepository::tagSetter);
            return giftCertificateWithTags.stream().map(certificateMapper::toGiftCertificateDTO).toList();
        } catch (DataAccessException ex) {
            log.error("failed to filter certificate with params, cause {}", ex.getMessage());
            throw new ServiceException("could not update certificate ", ex);
        }
    }
}
