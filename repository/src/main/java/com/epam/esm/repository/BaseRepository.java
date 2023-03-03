package com.epam.esm.repository;

import com.epam.esm.exception.RepositoryException;

import java.util.List;
import java.util.Optional;

public interface BaseRepository<T> {
    List<T> getAll() throws RepositoryException;
    Optional<T> getById(long id) throws RepositoryException;
    boolean save(T t) throws RepositoryException;
    boolean delete(long id) throws RepositoryException;
}
