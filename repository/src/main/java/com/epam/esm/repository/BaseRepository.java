package com.epam.esm.repository;

import java.util.List;
import java.util.Optional;

public interface BaseRepository<T> {
    List<T> getAll();
    Optional<T> getById(long id);
    boolean delete(long id);
}
