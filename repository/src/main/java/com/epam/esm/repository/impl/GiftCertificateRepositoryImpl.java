package com.epam.esm.repository.impl;

import com.epam.esm.enums.TableQueries;
import com.epam.esm.exception.RepositoryException;
import com.epam.esm.mapper.GiftCertificateRowMapper;
import com.epam.esm.mapper.TagRowMapper;
import com.epam.esm.model.GiftCertificate;
import com.epam.esm.repository.GiftCertificateRepository;
import lombok.extern.java.Log;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.TreeSet;

@Log
@Repository
public class GiftCertificateRepositoryImpl implements GiftCertificateRepository<GiftCertificate> {

    private final JdbcTemplate jdbcTemplate;
    private final TagRowMapper tagRowMapper;
    private final GiftCertificateRowMapper certificateRowMapper;

    public GiftCertificateRepositoryImpl(JdbcTemplate jdbcTemplate, TagRowMapper tagRowMapper, GiftCertificateRowMapper certificateRowMapper) {
        this.jdbcTemplate = jdbcTemplate;
        this.tagRowMapper = tagRowMapper;
        this.certificateRowMapper = certificateRowMapper;
    }

    @Override
    public List<GiftCertificate> getAll() throws RepositoryException {
        List<GiftCertificate> giftCertificates;
        try {
            log.info(">>>>>> Loading ....  <<<<<<");
            giftCertificates = jdbcTemplate.query(TableQueries.GET_ALL_GIFT_CERTIFICATES.getQuery(), certificateRowMapper);

            giftCertificates.forEach(this::setAllTags);
        }
        catch (DataAccessException e){
            log.info(">>>>>> Something is wrong - " + e.getMessage());
            throw new RepositoryException(e);
        }
        return giftCertificates;
    }

    @Override
    public Optional<GiftCertificate> getById(long id) throws RepositoryException {
        return Optional.empty();
    }

    @Override
    public boolean save(GiftCertificate giftCertificate) throws RepositoryException {
        return false;
    }

    @Override
    public boolean delete(long id) throws RepositoryException {
        return false;
    }

    @Override
    public boolean update(GiftCertificate giftCertificate) throws RepositoryException {
        return false;
    }

    private void setAllTags(GiftCertificate certificate) {
        certificate.setTags(new TreeSet<>(jdbcTemplate.query(
                TableQueries.GET_ALL_GIFT_CERTIFICATES_TAGS.getQuery(), tagRowMapper, certificate.getId())));
    }

}
