package com.epam.esm.service.impl;

import com.epam.esm.dto.GiftCertificateDTO;
import com.epam.esm.dto.TagDTO;
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
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
@Slf4j
public class GiftCertificateServiceImpl implements GiftCertificateService<GiftCertificateDTO> {
    private final GiftCertificateRepository<GiftCertificate> giftCertificateRepository;
    private final TagRepository<Tag> tagRepository;
    private final GiftCertificateTagRepository giftCertificateTagRepository;
    protected final GiftCertificateMapper certificateMapper;
    protected final TagMapper tagMapper;

    public GiftCertificateServiceImpl(GiftCertificateRepository<GiftCertificate> giftCertificateRepository, TagRepository<Tag> tagRepository, GiftCertificateTagRepository giftCertificateTagRepository, GiftCertificateMapper certificateMapper, TagMapper tagMapper) {
        this.giftCertificateRepository = giftCertificateRepository;
        this.tagRepository = tagRepository;
        this.giftCertificateTagRepository = giftCertificateTagRepository;
        this.certificateMapper = certificateMapper;
        this.tagMapper = tagMapper;
    }

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
                    return new ResourceNotFoundException(errorMessage);
                });
    }

    @Override
    public boolean save(GiftCertificateDTO giftCertificateDTO) throws ServiceException {
        log.info("> > > { Add New Gift Certificate }");
        try {
            GiftCertificate giftCertificate = certificateMapper.toGiftCertificate(giftCertificateDTO);
            Long certificateId = giftCertificateRepository.save(giftCertificate);

            if (giftCertificateDTO.getTags() != null) {

                giftCertificateDTO.getTags().forEach(tag -> {

                    tag.setId(tagRepository.save(tagMapper.toTag(tag))
                    );

                    // Adding tag and certificate details to join table.
                    giftCertificateTagRepository.save(new GiftCertificateTag(
                            certificateId, tag.getId()));
                });
            }

            // giftCertificateRepository.tagSetter(certificate);  --> Not important method was used before.

            return true;
        } catch (DataAccessException e) {
            log.error("Could not create gift certificate - > {}", e.getMessage());
            throw new ServiceException("Error saving resource", e, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public boolean delete(long id) throws ResourceNotFoundException {
        try {
            log.info("{ Deleting Gift Certificate }");
            giftCertificateTagRepository.delete(id);
            giftCertificateRepository.delete(id);

        } catch (DataAccessException ex) {
            log.error("cannot delete gift certificate by id {}, msg {}", id, ex.getMessage());
            throw new ResourceNotFoundException("Certificate could not be deleted! ", ex);
        }
        return true;
    }

    @Override
    public void update(GiftCertificateDTO giftCertificateDTO) throws ResourceNotFoundException {
        long id = giftCertificateDTO.getId();
        try {
            log.info("> > > { Updating a certificate } < < <");

            //  Adding tags to tag table from tags list in CertificateDTO

            List<TagDTO> tags = giftCertificateDTO.getTags();
            for (TagDTO tag : tags) {
                Optional<Tag> byId = tagRepository.getById(tag.getId());
                if (byId.isEmpty()) {
                    tag.setId(tagRepository.save(tagMapper.toTag(tag)));
                }
            }

            GiftCertificate certificate = certificateMapper.toGiftCertificate(giftCertificateDTO);

            // Updating Certificate and deleting the tag relation
            giftCertificateRepository.update(certificate);

            giftCertificateTagRepository.delete(certificate.getId());

            // Adding tag and certificate details to join table.
            certificate.getTags()
                    .forEach(tag -> {
                        giftCertificateTagRepository.save(new GiftCertificateTag(
                                certificate.getId(), tag.getId()));
                    });

            System.out.println(certificate.getTags());

            // sets all the tags to certificate
            giftCertificateRepository.tagSetter(certificate);


        } catch (DataAccessException ex) {
            log.error("failed to update certificate with id {}, cause {}", id, ex.getMessage());
            throw new ResourceNotFoundException("could not update certificate ", ex);
        }
    }


    @Override
    public List<GiftCertificateDTO> getGiftCertificateWithTags(String name, String tagName, String description, String sort) {
        List<GiftCertificate> giftCertificateWithTags = giftCertificateRepository.getGiftCertificateWithTags(name, tagName, description, sort);
        System.out.println(giftCertificateWithTags);
        System.out.println(giftCertificateWithTags.stream().map(certificateMapper::toGiftCertificateDTO).toList());
        return giftCertificateWithTags.stream().map(certificateMapper::toGiftCertificateDTO).toList();
    }
}
