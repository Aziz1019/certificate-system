package com.epam.esm.mapper;

import com.epam.esm.enums.TableColumns;
import com.epam.esm.model.Tag;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;

/**

 This class implements the RowMapper interface to map a row of the ResultSet to a Tag object.
 */
@Component
public class TagRowMapper implements RowMapper<Tag> {
    @Override
    public Tag mapRow(ResultSet rs, int rowNum) throws SQLException {
        return Tag.builder()
                .id(rs.getLong(TableColumns.TAG_TABLE_ID.getColumnName()))
                .name(rs.getString(TableColumns.TAG_TABLE_NAME.getColumnName()))
                .build();
    }
}
