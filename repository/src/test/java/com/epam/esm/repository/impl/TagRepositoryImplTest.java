package com.epam.esm.repository.impl;

import com.epam.esm.config.TestRepositoryConfig;
import com.epam.esm.model.Tag;
import com.epam.esm.repository.TagRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {TestRepositoryConfig.class})
class TagRepositoryImplTest {

    @Autowired
    private TagRepository tagRepository;

    @Test
    void bGetAll() {
        assertEquals(4, tagRepository.getAll().size());
    }

    @Test
    void getById() {
        Optional<Tag> optionalTag = tagRepository.getById(2);
        assertTrue(optionalTag.isPresent());

        Optional<Tag> byId = tagRepository.getById(6);
        assertEquals(Optional.empty(), byId);
    }


    @Test
    void testDelete() {
        Optional<Tag> beforeDelete = tagRepository.getById(1);
        tagRepository.delete(1);
        Optional<Tag> afterDelete = tagRepository.getById(1);

        assertFalse(afterDelete.isPresent());
        assertNotEquals(beforeDelete, afterDelete);
    }
}