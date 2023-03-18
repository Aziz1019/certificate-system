package com.epam.esm.repository;

import com.epam.esm.model.GiftCertificateTag;

import java.util.List;

public interface GiftCertificateTagRepository {
    List<GiftCertificateTag> getAll();
    boolean save(GiftCertificateTag giftCertificateTag);
    boolean delete(long id);
    boolean deleteByTagId(long id);

}
