package com.epam.esm.repository.impl;

import com.epam.esm.enums.TableQueries;
import com.epam.esm.mapper.GiftCertificateRowMapper;
import com.epam.esm.mapper.TagRowMapper;
import com.epam.esm.model.GiftCertificate;
import com.epam.esm.model.Tag;
import com.epam.esm.repository.GiftCertificateRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementSetter;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

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
    public Long save(GiftCertificate giftCertificate) {
        log.info("> > >  { Creating / Adding new giftCertificate data }");
        return jdbcTemplate.queryForObject(TableQueries.SAVE_GIFT_CERTIFICATES.getQuery(), Long.class,
                giftCertificate.getName(),
                giftCertificate.getDescription(),
                giftCertificate.getPrice(),
                giftCertificate.getDuration());
    }

    @Override
    public List<GiftCertificate> getGiftCertificateWithTags(String name, String tagName, String description, String sort) {

        String sorted = switch (sort) {
            case "name_desc" -> "name DESC";
            case "date_asc" -> "create_date ASC";
            case "date_desc" -> "create_date DESC";
            default -> "name ASC";
        };

        String query = "SELECT DISTINCT gc.*\n" +
                "FROM gift_certificate gc\n" +
                "         LEFT JOIN gift_certificate_tag gct ON gc.id = gct.certificate_id\n" +
                "         LEFT JOIN Tag t ON gct.tag_id = t.id\n" +
                " WHERE (gc.name ILIKE '%" + nullChecker(name) + "%')\n" +
                " AND (gc.description ILIKE '%" + nullChecker(description) + "%')\n" +
                " AND (t.name ILIKE '%" + nullChecker(tagName) + "%')" +
                " ORDER BY " + sorted;

        return jdbcTemplate.query(query, certificateRowMapper);
    }

    @Override
    public boolean delete(long id) {
        log.info("> > > Loading { Deleting GiftCertificates By ID } ");
        return jdbcTemplate.update(TableQueries.DELETE_GIFT_CERTIFICATE.getQuery(), id) > 0;
    }

    @Override
    public void tagSetter(GiftCertificate giftCertificate) {
        setAllTags(giftCertificate);
    }

    @Override
    public void update(GiftCertificate giftCertificate) {
        log.info("> > > Loading { Updating GiftCertificates By ID } ");
        int updated = jdbcTemplate.update(TableQueries.UPDATE_GIFT_CERTIFICATES.getQuery(),
                giftCertificate.getName(),
                giftCertificate.getDescription(),
                giftCertificate.getPrice(),
                giftCertificate.getDuration(),
                giftCertificate.getId());
    }

    private void setAllTags(GiftCertificate certificate) {
        certificate.setTags(new TreeSet<>(jdbcTemplate.query(
                TableQueries.GET_ALL_GIFT_CERTIFICATES_TAGS.getQuery(), tagRowMapper, certificate.getId())));
    }

    private String nullChecker(String name){
        return !StringUtils.hasLength(name) ? "" : name;
    }


}
