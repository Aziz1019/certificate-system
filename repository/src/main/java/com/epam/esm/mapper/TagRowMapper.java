package com.epam.esm.mapper;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;
import java.sql.ResultSet;
import java.sql.SQLException;
import com.epam.esm.enums.TableColumns;
import com.epam.esm.model.Tag;
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
