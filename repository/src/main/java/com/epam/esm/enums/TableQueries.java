package com.epam.esm.enums;

/**
 * This enum represents the SQL queries used in the project.
 * <p>
 * It contains constants for all the SQL statements used for retrieving,
 * <p>
 * inserting, updating and deleting data from the database tables.
 */
public enum TableQueries {

    /**
     * SQL statements for retrieving gift certificates from the database.
     */
    GET_ALL_GIFT_CERTIFICATES("select * from gift_certificate order by id"),
    GET_GIFT_CERTIFICATES_BY_ID("select * from gift_certificate where id = ?"),
    GET_GIFT_CERTIFICATE_TAG_BY_TAG_ID("select * from gift_certificate_tag where tag_id = ?"),
    GET_GIFT_CERTIFICATES_BY_NAME("select * from gift_certificate where name like ?"),
    GET_GIFT_CERTIFICATES_BY_DESCRIPTION("select * from gift_certificate where description like ?"),

    /**
     * SQL statements for inserting and updating gift certificates in the database.
     */
    SAVE_GIFT_CERTIFICATES("insert into gift_certificate (name, description, price, duration) values (?, ?, ?, ?) on conflict do nothing returning id"),
    UPDATE_GIFT_CERTIFICATES("update gift_certificate set name = coalesce(?, name), description = coalesce(?, description), price = coalesce(?, price), duration = coalesce(?, duration) where id = ?"),

    /**
     * SQL statements for deleting gift certificates from the database.
     */
    DELETE_GIFT_CERTIFICATE("delete from gift_certificate where id = ?"),

    /**
     * SQL statements for retrieving tags from the database.
     */

    GET_ALL_TAGS("select * from tag"),
    GET_TAGS_BY_ID("select * from tag where id = ?"),
    GET_TAG_ID_BY_NAME("select id from tag where name = ?"),

    /**
     * SQL statements for inserting, updating and deleting tags in the database.
     */
    SAVE_TAG_NAME("insert into tag (name) values (?) on conflict do nothing returning id"),
    DELETE_TAG_BY_ID("delete from tag where id = ?"),

    UPDATE_TAG_BY_ID("update tag set name = ? where id = ?"),

    /**
     * SQL statements for retrieving, inserting and deleting join table entries (gift_certificate_tag)
     * that link gift certificates to tags in the database.
     */

    GET_ALL_GIFT_CERTIFICATES_TAGS("select * from gift_certificate_tag gc inner join tag t on t.id = gc.tag_id where certificate_id = ?"),
    GET_GIFT_CERTIFICATES_BY_TAGS("select c.id, c.name, description, price, duration, create_date, last_update_date from gift_certificate c inner join gift_certificate_tag ct on c.id = ct.certificate_id inner join tag t on t.id = ct.tag_id "),
    SAVE_TAGS_TO_GIFT_CERTIFICATES("insert into gift_certificate_tag (certificate_id, tag_id) values(?, ?)"),
    DELETE_JOIN_TABLE_WITH_CERTIFICATE_ID("delete from gift_certificate_tag where certificate_id = ?"),
    DELETE_JOIN_TABLE_WITH_TAG_ID("delete from gift_certificate_tag where tag_id = ?");

    private final String query;

    TableQueries(String query) {
        this.query = query;
    }

    public String getQuery() {
        return query;
    }
}
