package com.epam.esm.enums;
/**

 Enum class that defines the column names for the tables in the database.
 */
public enum TableColumns {
    TAG_TABLE_ID("id"),
    TAG_TABLE_NAME("name"),
    GIFT_CERTIFICATE_ID("id"),
    GIFT_CERTIFICATE_NAME("name"),
    GIFT_CERTIFICATE_DESCRIPTION("description"),
    GIFT_CERTIFICATE_PRICE("price"),
    GIFT_CERTIFICATE_DURATION("duration"),
    GIFT_CERTIFICATE_CREATE_DATE("create_date"),
    GIFT_CERTIFICATE_LAST_UPDATE_DATE("last_update_date");

    private final String columnName;

    TableColumns(String columnName) {
        this.columnName = columnName;
    }

    public String getColumnName() {
        return columnName;
    }
}