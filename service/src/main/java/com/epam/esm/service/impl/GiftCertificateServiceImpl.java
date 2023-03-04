package com.epam.esm.service.impl;

import com.epam.esm.dto.GiftCertificateDTO;
import com.epam.esm.dto.TagDTO;
import com.epam.esm.exception.ResourceNotFoundException;
import com.epam.esm.exception.ServiceException;
import com.epam.esm.mapper.GiftCertificateMapper;
import com.epam.esm.mapper.TagMapper;
import com.epam.esm.model.GiftCertificate;
import com.epam.esm.model.Tag;
import com.epam.esm.repository.GiftCertificateRepository;
import com.epam.esm.repository.TagRepository;
import com.epam.esm.service.GiftCertificateService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataAccessException;
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
    protected final GiftCertificateMapper certificateMapper;
    protected final TagMapper tagMapper;

    public GiftCertificateServiceImpl(GiftCertificateRepository<GiftCertificate> giftCertificateRepository, TagRepository<Tag> tagRepository, GiftCertificateMapper certificateMapper, TagMapper tagMapper) {
        this.giftCertificateRepository = giftCertificateRepository;
        this.tagRepository = tagRepository;
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
        GiftCertificate giftCertificate = certificateMapper.toGiftCertificate(giftCertificateDTO);
        giftCertificateRepository.save(giftCertificate);
//        giftCertificateDTO.getTags().stream().forEach(tag -> tag.setId());
        return false;
    }

    @Override
    public boolean delete(long id) throws ResourceNotFoundException {
        return false;
    }

    @Override
    public boolean update(GiftCertificateDTO giftCertificateDTO) throws ResourceNotFoundException {
        return false;
    }

    @Override
    public List<GiftCertificateDTO> getByTag(TagDTO tag) throws ResourceNotFoundException {
        return null;
    }
}
