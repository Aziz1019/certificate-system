package com.epam.esm.repository.impl;

import com.epam.esm.enums.TableQueries;
import com.epam.esm.mapper.GiftCertificateRowMapper;
import com.epam.esm.mapper.TagRowMapper;
import com.epam.esm.model.GiftCertificate;
import com.epam.esm.model.GiftCertificateTag;
import com.epam.esm.model.Tag;
import com.epam.esm.repository.GiftCertificateRepository;
import com.epam.esm.repository.GiftCertificateTagRepository;
import com.epam.esm.repository.TagRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.Optional;
import java.util.TreeSet;


@Slf4j
@Repository
public class GiftCertificateRepositoryImpl implements GiftCertificateRepository {
    private final JdbcTemplate jdbcTemplate;
    private final TagRowMapper tagRowMapper;
    private final GiftCertificateRowMapper certificateRowMapper;
    
    private final TagRepository tagRepository;
    
    private final GiftCertificateTagRepository certificateTagRepository;

    public GiftCertificateRepositoryImpl(JdbcTemplate jdbcTemplate, TagRowMapper tagRowMapper, GiftCertificateRowMapper certificateRowMapper, TagRepository tagRepository, GiftCertificateTagRepository certificateTagRepository) {
        this.jdbcTemplate = jdbcTemplate;
        this.tagRowMapper = tagRowMapper;
        this.certificateRowMapper = certificateRowMapper;
        this.tagRepository = tagRepository;
        this.certificateTagRepository = certificateTagRepository;
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
        GiftCertificate giftCertificate;
        try {
            giftCertificate = jdbcTemplate.queryForObject(TableQueries.GET_GIFT_CERTIFICATES_BY_ID.getQuery(), certificateRowMapper, id);
            if (giftCertificate != null) {
                setAllTags(giftCertificate);
                return Optional.of(giftCertificate);
            }
        } catch (EmptyResultDataAccessException e) {
            log.error(e.getMessage());
        }
        return Optional.empty();
    }

    @Override
    public Long save(GiftCertificate giftCertificate) {
        log.info("> > >  { Creating / Adding new giftCertificate data }");
        Long id = jdbcTemplate.queryForObject(TableQueries.SAVE_GIFT_CERTIFICATES.getQuery(), Long.class,
                giftCertificate.getName(),
                giftCertificate.getDescription(),
                giftCertificate.getPrice(),
                giftCertificate.getDuration());

        if (giftCertificate.getTags() != null) {
            joinTags(giftCertificate);
        }
        return id;
    }

    @Override
    public List<GiftCertificate> getGiftCertificateWithTags(String name, String tagName, String description, String sort) {

        String sorted = switch (sort) {
            case "name_desc" -> "name DESC";
            case "date_asc" -> "create_date";
            case "date_desc" -> "create_date DESC";
            case "name_date_desc" -> "name, create_date DESC";
            case "name_date" -> "name, create_date";
            default -> "name";
        };

        String query = " SELECT gc.* FROM gift_certificate gc LEFT JOIN gift_certificate_tag gct ON gc.id = gct.certificate_id"
                            + " LEFT JOIN Tag t ON gct.tag_id = t.id"
                            + " WHERE (t.name ILIKE '%"+nullChecker(tagName)+"%' AND (gc.description ILIKE '%"+nullChecker(description)+"%')"
                            + " AND (gc.name ILIKE '%"+nullChecker(name)+"%')) order by " + sorted;

        List<GiftCertificate> certificates = jdbcTemplate.query(query, certificateRowMapper);

        // Because we joined tag tables, if column without tag is searched due to joins it may not appear
        // That's why we check for tagName, if it was null, we do our filter again this time without tag and its joins.
        // Else we return empty or full certificates and let the Service layer deal with the rest.

        if(certificates.isEmpty()){
            if(tagName == null){
                String updatedQuery = "SELECT gc.*" +
                        "FROM gift_certificate gc" +
                        " WHERE (gc.name ILIKE '%" + nullChecker(name) + "%')" +
                        " AND (gc.description ILIKE '%" + nullChecker(description) + "%')" +
                        " ORDER BY " + sorted;
                return jdbcTemplate.query(updatedQuery, certificateRowMapper);
            }
            else 
                return certificates;
        }
        return certificates;
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
    public void update(GiftCertificate certificate) {
        log.info("> > > Loading { Updating GiftCertificates By ID } ");
        jdbcTemplate.update(TableQueries.UPDATE_GIFT_CERTIFICATES.getQuery(),
                certificate.getName(),
                certificate.getDescription(),
                certificate.getPrice(),
                certificate.getDuration(),
                certificate.getId());

        if (certificate.getTags() != null) {
            joinTags(certificate);
        }
    }

    private void joinTags(GiftCertificate certificate) {
        certificate.getTags().forEach(tag -> {
            Optional<Tag> byId = tagRepository.getById(tag.getId());
            if (byId.isEmpty()) {
                tag.setId(tagRepository.save(tag));
            }
        });
        // Using Join table for joining tag and certificate
        certificate.getTags()
                .forEach(tag -> certificateTagRepository.save(new GiftCertificateTag(
                        certificate.getId(), tag.getId())));
    }

    public void setAllTags(GiftCertificate certificate) {
        certificate.setTags(new TreeSet<>(jdbcTemplate.query(
                TableQueries.GET_ALL_GIFT_CERTIFICATES_TAGS.getQuery(), tagRowMapper, certificate.getId())));
    }

    public String nullChecker(String name) {
        return !StringUtils.hasLength(name) ? "" : name;
    }


}
