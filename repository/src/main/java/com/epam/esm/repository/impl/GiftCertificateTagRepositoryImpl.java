package com.epam.esm.repository.impl;

import com.epam.esm.enums.TableQueries;
import com.epam.esm.model.GiftCertificateTag;
import com.epam.esm.repository.GiftCertificateTagRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@Repository
@Slf4j
public class GiftCertificateTagRepositoryImpl implements GiftCertificateTagRepository {
    private final JdbcTemplate jdbcTemplate;

    public GiftCertificateTagRepositoryImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
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
        int update = jdbcTemplate.update(TableQueries.DELETE_JOIN_TABLE_WITH_CERTIFICATE_ID.getQuery(), id);
        return update > 0;
    }

    @Override
    public void deleteByTagId(long id) {
        jdbcTemplate.update(TableQueries.DELETE_JOIN_TABLE_WITH_TAG_ID.getQuery(), id);
    }
}
