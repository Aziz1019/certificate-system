package com.epam.esm.mapper;

import com.epam.esm.model.GiftCertificate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;

import static com.epam.esm.enums.TableColumns.*;

@Component
public class GiftCertificateRowMapper implements RowMapper<GiftCertificate> {
    @Override
    public GiftCertificate mapRow(ResultSet rs, int rowNum) throws SQLException {
        return GiftCertificate.builder()
                .id(rs.getLong(GIFT_CERTIFICATE_ID.getColumnName()))
                .name(rs.getString(GIFT_CERTIFICATE_NAME.getColumnName()))
                .description(rs.getString(GIFT_CERTIFICATE_DESCRIPTION.getColumnName()))
                .price(rs.getDouble(GIFT_CERTIFICATE_PRICE.getColumnName()))
                .duration(rs.getLong(GIFT_CERTIFICATE_DURATION.getColumnName()))
                .createDate(String.valueOf(rs.getTimestamp(GIFT_CERTIFICATE_CREATE_DATE.getColumnName())))
                .lastUpdateDate(String.valueOf(rs.getTimestamp(GIFT_CERTIFICATE_LAST_UPDATE_DATE.getColumnName())))
                .build();
    }
}
