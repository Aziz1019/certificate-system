package com.epam.esm.repository;

import java.util.List;
import java.util.Optional;


/**

 This interface defines basic CRUD operations for repositories that work with entities of type T.

 @param <T> The type of entity that the repository works with.
 */

public interface BaseRepository<T> {
    List<T> getAll();

    Optional<T> getById(long id);

    boolean delete(long id);
}
