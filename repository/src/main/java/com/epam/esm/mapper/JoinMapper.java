package com.epam.esm.mapper;

import com.epam.esm.model.GiftCertificateTag;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;

@Component
public class JoinMapper implements RowMapper<GiftCertificateTag> {

    @Override
    public GiftCertificateTag mapRow(ResultSet rs, int rowNum) throws SQLException {
        return GiftCertificateTag.builder().certificateId(rs.getLong("certificate_id"))
                .tagId(rs.getLong("tag_id")).build();
    }
}
