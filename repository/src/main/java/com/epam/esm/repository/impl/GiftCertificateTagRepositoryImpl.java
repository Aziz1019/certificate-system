package com.epam.esm.repository.impl;

import com.epam.esm.enums.TableQueries;
import com.epam.esm.mapper.JoinMapper;
import com.epam.esm.model.GiftCertificateTag;
import com.epam.esm.repository.GiftCertificateTagRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Slf4j
public class GiftCertificateTagRepositoryImpl implements GiftCertificateTagRepository {
    private final JoinMapper joinMapper;
    private final JdbcTemplate jdbcTemplate;

    public GiftCertificateTagRepositoryImpl(JoinMapper joinMapper, JdbcTemplate jdbcTemplate) {
        this.joinMapper = joinMapper;
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<GiftCertificateTag> getAll() {
        log.info("> > > Loading { Get ALL Gift Certificates and Tags } ");
        return jdbcTemplate.query(TableQueries.GET_ALL_GIFT_CERTIFICATES_TAGS.getQuery(), joinMapper);

    }

    @Override
    public boolean save(GiftCertificateTag giftCertificateTag) {
        log.info("> > > { Creating / Adding new giftCertificate data } < < <");
        int update = jdbcTemplate.update(TableQueries.SAVE_TAGS_TO_GIFT_CERTIFICATES.getQuery(),
                giftCertificateTag.getCertificateId(),
                giftCertificateTag.getTagId()
        );
        return update > 0;
    }

    @Override
    public boolean delete(long id) {
        log.info("> > > { Deleting Join Table } < < <");
        int update = jdbcTemplate.update(TableQueries.DELETE_GIFT_CERTIFICATE_TAGS.getQuery(), joinMapper, id);
        return update > 0;
    }
}
