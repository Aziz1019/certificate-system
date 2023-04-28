package com.epam.esm.repository;

import com.epam.esm.model.Tag;

/**
 The TagRepository interface provides methods for CRUD operations on the Tag entity in the database.
 This interface extends the BaseRepository interface, which provides basic CRUD operations on any entity.
 */

public interface TagRepository extends BaseRepository<Tag> {
    /**
     Saves the given Tag entity to the database.
     @param t the Tag entity to be saved
     @return the ID of the newly created tag
     */
    Long save(Tag t);
    void update(Tag t);
}
