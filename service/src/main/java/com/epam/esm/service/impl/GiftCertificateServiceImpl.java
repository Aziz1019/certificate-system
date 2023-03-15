package com.epam.esm.service.impl;

import com.epam.esm.dto.GiftCertificateDTO;
import com.epam.esm.dto.TagDTO;
import com.epam.esm.enums.TableQueries;
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
            boolean save = giftCertificateRepository.save(giftCertificate);
            giftCertificateDTO.getTags().forEach(tag -> {
                tagRepository.save(tagMapper.toTag(tag));
            });
            giftCertificate.getTags().forEach(tag -> {
                giftCertificateTagRepository.save(new GiftCertificateTag(
                        giftCertificate.getId(), tag.getId()));
            });
            giftCertificateRepository.tagSetter(giftCertificate);
            return save;
        } catch (DataAccessException e) {
            log.error("Could not create gift certificate - > {}", e.getMessage());
            throw new ServiceException("Error saving resource", e, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public boolean delete(long id) throws ResourceNotFoundException {
        try {

            log.info("{ Deleting Gift Certificate }");

            giftCertificateRepository.getById(id);

            giftCertificateRepository.delete(id);

        } catch (EmptyResultDataAccessException ex) {
            log.error("Cannot find gift certificate by id {}, msg: {}", id, ex.getMessage());
            throw new ResourceNotFoundException("Certificate not found!");
        } catch (DataAccessException ex) {
            log.error("cannot delete gift certificate by id {}, msg {}", id, ex.getMessage());
            throw new ResourceNotFoundException("Certificate could not be deleted! ", ex);
        }
        return true;
    }

    @Override
    public boolean update(GiftCertificateDTO giftCertificateDTO) throws ResourceNotFoundException {
        long id = giftCertificateDTO.getId();
        try {
            log.info("> > > { Updating a certificate } < < <");

            giftCertificateRepository.getById(id);

            GiftCertificate certificate = certificateMapper.toGiftCertificate(giftCertificateDTO);

            giftCertificateRepository.update(certificate);

            giftCertificateTagRepository.delete(certificate.getId());

            giftCertificateDTO.getTags().forEach(tag -> tagRepository.save(tagMapper.toTag(tag)));

            certificate.getTags()
                    .forEach(tag -> {
                        giftCertificateTagRepository.save(new GiftCertificateTag(
                                certificate.getId(), tag.getId()));
                    });

            giftCertificateRepository.tagSetter(certificate);

        } catch (EmptyResultDataAccessException ex) {
            log.error("could not get gift certificate by id {}, msg {}", id, ex.getMessage());
            throw new ResourceNotFoundException("could not get gift certificate, id:");
        } catch (DataAccessException ex) {
            log.error("failed to update certificate with id {}, cause {}", id, ex.getMessage());
            throw new ResourceNotFoundException("could not update certificate ", ex);
        }
        return true;
    }

    @Override
    public List<GiftCertificateDTO> getByTag(TagDTO tag) throws ResourceNotFoundException {
        try {
            log.info("> > > { Getting Tags }");
            return giftCertificateRepository.getByTag(tagMapper.toTag(tag)).stream()
                    .map(certificateMapper::toGiftCertificateDTO).toList();
        } catch (DataAccessException ex) {
            log.error("could not find gift certificate by tags {}", ex.getMessage());
            throw new ResourceNotFoundException("Gift Certificate was not found!", ex);
        }
    }

}
