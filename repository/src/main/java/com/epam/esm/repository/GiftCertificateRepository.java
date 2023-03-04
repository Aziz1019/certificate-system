package com.epam.esm.repository;

import com.epam.esm.model.GiftCertificate;

public interface GiftCertificateRepository<T> extends BaseRepository<T> {
    void addTags(GiftCertificate giftCertificate);
    boolean update(T t);
}
