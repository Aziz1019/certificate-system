package com.epam.esm.repository.impl;

import com.epam.esm.enums.TableQueries;
import com.epam.esm.exception.RepositoryException;
import com.epam.esm.mapper.TagRowMapper;
import com.epam.esm.model.Tag;
import com.epam.esm.repository.TagRepository;
import lombok.extern.java.Log;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Log
@Repository
public class TagRepositoryImpl implements TagRepository<Tag> {
    private final JdbcTemplate jdbcTemplate;
    private final TagRowMapper tagRowMapper;

    public TagRepositoryImpl(JdbcTemplate jdbcTemplate, TagRowMapper tagRowMapper) {
        this.jdbcTemplate = jdbcTemplate;
        this.tagRowMapper = tagRowMapper;
    }

    @Override
    public List<Tag> getAll() throws RepositoryException {
        List<Tag> tags;
        try {
            log.info(">>>>>> Loading ....  <<<<<<");
            tags = jdbcTemplate.query(TableQueries.GET_ALL_TAGS.getQuery(), tagRowMapper);
        }
        catch (DataAccessException e){
            log.info(">>>>>> Something is wrong - " + e.getMessage());
            throw new RuntimeException();
        }
        return tags;
    }

    @Override
    public Optional<Tag> getById(long id) throws RepositoryException {
        return Optional.empty();
    }

    @Override
    public boolean save(Tag tag) throws RepositoryException {
        return false;
    }

    @Override
    public boolean delete(long id) throws RepositoryException {
        return false;
    }
}
