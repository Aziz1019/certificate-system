package com.epam.esm.repository;

/**
 The TagRepository interface provides methods for CRUD operations on the Tag entity in the database.
 This interface extends the BaseRepository interface, which provides basic CRUD operations on any entity.
 @param <T> the type parameter for the entity that is being handled by this repository
 */

public interface TagRepository<T> extends BaseRepository<T> {
    /**
     Saves the given Tag entity to the database.
     @param t the Tag entity to be saved
     @return the ID of the newly created tag
     */
    Long save(T t);
}
