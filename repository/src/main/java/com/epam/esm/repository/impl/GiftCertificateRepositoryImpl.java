package com.epam.esm.repository.impl;
import com.epam.esm.enums.TableQueries;
import com.epam.esm.mapper.GiftCertificateRowMapper;
import com.epam.esm.mapper.TagRowMapper;
import com.epam.esm.model.GiftCertificate;
import com.epam.esm.repository.GiftCertificateRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;
import java.util.TreeSet;

@Slf4j
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
    public List<GiftCertificate> getAll() {
        log.info("> > > Loading { Get ALL Gift Certificates } ");
        List<GiftCertificate> giftCertificates = jdbcTemplate.query(TableQueries.GET_ALL_GIFT_CERTIFICATES.getQuery(), certificateRowMapper);
        giftCertificates.forEach(this::setAllTags);
        return giftCertificates;
    }

    @Override
    public Optional<GiftCertificate> getById(long id) {
        log.info("> > > Loading { Get Gift Certificate By ID }");
        GiftCertificate giftCertificate = jdbcTemplate.queryForObject(TableQueries.GET_GIFT_CERTIFICATES_BY_ID.getQuery(), certificateRowMapper, id);
        if (giftCertificate == null) {
            return Optional.empty();
        }
        setAllTags(giftCertificate);
        return Optional.of(giftCertificate);
    }

    @Override
    public boolean save(GiftCertificate giftCertificate) {
        log.info("> > > Creating / Adding new giftCertificate data . . . < < <");
        int update = jdbcTemplate.update(TableQueries.SAVE_GIFT_CERTIFICATES.getQuery(),
                giftCertificate.getName(),
                giftCertificate.getDescription(),
                giftCertificate.getPrice(),
                giftCertificate.getDuration());
        return update > 0;
    }

    @Override
    public boolean delete(long id) {
        log.info("> > > Loading { Deleting GiftCertificates By ID } ");
        return jdbcTemplate.update(TableQueries.UPDATE_GIFT_CERTIFICATES.getQuery(), id) > 0;
    }

    @Override
    public void insertTags(GiftCertificate certificate) {
    }

    @Override
    public boolean update(GiftCertificate giftCertificate) {
        log.info("> > > Loading { Updating GiftCertificates By ID } ");
        int updated = jdbcTemplate.update(TableQueries.UPDATE_GIFT_CERTIFICATES.getQuery(),
                giftCertificate.getName(),
                giftCertificate.getDescription(),
                giftCertificate.getPrice(),
                giftCertificate.getDuration());

//        jdbcTemplate.update(TableQueries.DELETE_GIFT_CERTIFICATE_TAGS_ALL.getQuery(),
//                giftCertificate.getId());

        return updated > 0;
    }

    private void setAllTags(GiftCertificate certificate) {
        certificate.setTags(new TreeSet<>(jdbcTemplate.query(
                TableQueries.GET_ALL_GIFT_CERTIFICATES_TAGS.getQuery(), tagRowMapper, certificate.getId())));
    }

}
