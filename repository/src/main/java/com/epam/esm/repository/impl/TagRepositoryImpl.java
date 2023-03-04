package com.epam.esm.repository.impl;

import com.epam.esm.enums.TableQueries;
import com.epam.esm.mapper.TagRowMapper;
import com.epam.esm.model.Tag;
import com.epam.esm.repository.TagRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Slf4j
@Repository
public class TagRepositoryImpl implements TagRepository<Tag> {
    private final JdbcTemplate jdbcTemplate;
    private final TagRowMapper tagRowMapper;

    public TagRepositoryImpl(JdbcTemplate jdbcTemplate, TagRowMapper tagRowMapper) {
        this.jdbcTemplate = jdbcTemplate;
        this.tagRowMapper = tagRowMapper;
    }

    @Override
    public List<Tag> getAll() {
        log.info(">>>>>> Loading { Getting Tags }  <<<<<<");
        return jdbcTemplate.query(TableQueries.GET_ALL_TAGS.getQuery(), tagRowMapper);
    }

    @Override
    public Optional<Tag> getById(long id) {
        log.info("> > > Loading {Getting Tags By ID}");
        return Optional.ofNullable(jdbcTemplate.queryForObject(TableQueries.GET_TAGS_BY_ID.getQuery(), tagRowMapper, id));
    }

    @Override
    public boolean save(Tag tag) {
        log.info("> > > Loading { Saving / Adding Tags }");
        int updated = jdbcTemplate.update(TableQueries.SAVE_TAG_NAME.getQuery(), tagRowMapper,
                tag.getName());
        return updated > 0;
    }

    @Override
    public boolean delete(long id) {
        log.info("> > > Loading { Deleting Tag By ID } ");
        int update = jdbcTemplate.update(TableQueries.DELETE_TAG_BY_ID.getQuery(), tagRowMapper, id);
        return update > 0;
    }
}
