package com.epam.esm.repository;

import com.epam.esm.model.GiftCertificate;

import java.util.List;

/**
 An interface for managing Gift Certificate objects in a database.
 Extends the {@link BaseRepository} interface.
 @param <T> a type parameter representing the Gift Certificate class or its subclass
 */
public interface GiftCertificateRepository<T> extends BaseRepository<T> {
    /**
     Sets tags for a given GiftCertificate object.
     */
    void tagSetter(GiftCertificate giftCertificate);
    /**
     Updates a given object in the database.
     @param t the object to update in the database
     */

    void update(T t);

    /**

     Saves a given object to the database.
     @param t the object to save to the database
     @return the ID of the saved object
     */

    Long save(T t);

    /**

     Returns a list of GiftCertificate objects with associated tags based on the given search parameters.
     @param name the name of the GiftCertificate to search for (can be null)
     @param tagName the name of the tag to search for (can be null)
     @param description the description of the GiftCertificate to search for (can be null)
     @param sort the sort order to use for the returned list
     @return a list of GiftCertificate objects with associated tags
     */
    List<GiftCertificate> getGiftCertificateWithTags(String name, String tagName, String description, String sort);
}
