package com.epam.esm.repository;

import com.epam.esm.model.GiftCertificateTag;

import java.util.List;

/**

 The GiftCertificateTagRepository interface provides methods to interact with the GiftCertificateTag table in the database.

 It allows performing CRUD operations on GiftCertificateTag entities, such as getting all GiftCertificateTag records,

 saving a new GiftCertificateTag, deleting a GiftCertificateTag by ID, and deleting GiftCertificateTag records by Tag ID.
 */

public interface GiftCertificateTagRepository {
     /**
     Saves a new GiftCertificateTag record to the database.
     @param giftCertificateTag the GiftCertificateTag to be saved.
     @return true if the GiftCertificateTag is saved successfully, false otherwise.
     */
    boolean save(GiftCertificateTag giftCertificateTag);

    /**
     Deletes a GiftCertificateTag record from the database by ID.
     @param id the ID of the GiftCertificateTag to be deleted.
     @return true if the GiftCertificateTag is deleted successfully, false otherwise.
     */
    boolean delete(long id);

    /**
     Deletes all GiftCertificateTag records from the database that are associated with a specified Tag ID.
     @param id the Tag ID of the GiftCertificateTag records to be deleted.
     */

    void deleteByTagId(long id);

}
