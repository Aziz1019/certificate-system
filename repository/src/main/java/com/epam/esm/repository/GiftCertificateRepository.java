package com.epam.esm.repository;

import com.epam.esm.model.GiftCertificate;

public interface GiftCertificateRepository<T> extends BaseRepository<T> {
    void insertTags(GiftCertificate certificate);
    boolean update(T t);
}
