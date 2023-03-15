package com.epam.esm.repository;

import com.epam.esm.model.GiftCertificate;
import com.epam.esm.model.Tag;

import java.util.List;

public interface GiftCertificateRepository<T> extends BaseRepository<T> {
    void tagSetter(GiftCertificate giftCertificate);
    void update(T t);
    List<GiftCertificate> getByTag(Tag tag);

}
