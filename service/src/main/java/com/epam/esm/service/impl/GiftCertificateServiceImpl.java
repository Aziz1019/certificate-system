package com.epam.esm.service.impl;

import com.epam.esm.dto.GiftCertificateDTO;
import com.epam.esm.exception.RepositoryException;
import com.epam.esm.mapper.GiftCertificateMapper;
import com.epam.esm.model.GiftCertificate;
import com.epam.esm.repository.GiftCertificateRepository;
import com.epam.esm.service.GiftCertificateService;
import lombok.extern.slf4j.Slf4j;
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
    protected final GiftCertificateMapper certificateMapper;

    public GiftCertificateServiceImpl(GiftCertificateRepository<GiftCertificate> giftCertificateRepository, GiftCertificateMapper certificateMapper) {
        this.giftCertificateRepository = giftCertificateRepository;
        this.certificateMapper = certificateMapper;
    }

    @Override
    public List<GiftCertificateDTO> getAll() {
        try {
            log.info("> > >  Loading Certificates . . .   < < <");
                return giftCertificateRepository.getAll()
                    .stream()
                    .map(certificateMapper::toGiftCertificateDTO)
                     .collect(Collectors.toList());
        } catch (RepositoryException e) {
            log.error("> > >  Problem occurred while getting certificates . . .   < < <" + e.getMessage());
            throw new RuntimeException(e);
        }
    }

    @Override
    public Optional<GiftCertificateDTO> getById(long id) {
        return Optional.empty();
    }

    @Override
    public boolean save(GiftCertificateDTO giftCertificateDTO) {
        return false;
    }

    @Override
    public boolean delete(long id) {
        return false;
    }

    @Override
    public boolean update(GiftCertificateDTO giftCertificateDTO) {
        return false;
    }
}
