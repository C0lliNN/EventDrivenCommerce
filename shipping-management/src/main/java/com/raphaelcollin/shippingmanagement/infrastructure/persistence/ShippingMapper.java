package com.raphaelcollin.shippingmanagement.infrastructure.persistence;

import com.raphaelcollin.shippingmanagement.domain.entity.Shipping;
import com.raphaelcollin.shippingmanagement.domain.entity.ShippingStatus;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;

@Component
public class ShippingMapper implements RowMapper<Shipping> {

    @Override
    public Shipping mapRow(final ResultSet rs, final int rowNum) throws SQLException {
        return Shipping.builder()
                .id(rs.getInt("id"))
                .upstreamExternalIdentifier(rs.getString("upstream_external_identifier"))
                .status(ShippingStatus.valueOf(rs.getString("status")))
                .lastUpdated(rs.getTimestamp("last_updated").toLocalDateTime())
                .build();
    }
}
