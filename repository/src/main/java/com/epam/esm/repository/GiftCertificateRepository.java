package com.epam.esm.repository;

import com.epam.esm.exception.RepositoryException;

public interface GiftCertificateRepository<T> extends BaseRepository<T> {
    boolean update(T t) throws RepositoryException;
}
