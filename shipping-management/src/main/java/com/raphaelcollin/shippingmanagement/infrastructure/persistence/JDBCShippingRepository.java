package com.raphaelcollin.shippingmanagement.infrastructure.persistence;

import com.raphaelcollin.shippingmanagement.application.ShippingRepository;
import com.raphaelcollin.shippingmanagement.domain.entity.Shipping;
import lombok.AllArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@AllArgsConstructor
public class JDBCShippingRepository implements ShippingRepository {
    private final JdbcTemplate template;
    private final RowMapper<Shipping> shippingRowMapper;

    @Override
    public void save(final Shipping shipping) {
        template.update("DELETE FROM shippings WHERE id = ?", shipping.getId());
        template.update(
                "INSERT INTO shippings VALUES (null, ?, ?, ?)",
                shipping.getUpstreamExternalIdentifier(),
                shipping.getStatus().name(),
                shipping.getLastUpdated()
        );
    }

    @Override
    public List<Shipping> findAll() {
        return template.query("SELECT * FROM shippings", shippingRowMapper);
    }

    @Override
    public Optional<Shipping> findById(final int id) {
        return template.query("SELECT * FROM shippings WHERE id = ?", shippingRowMapper, id).stream().findFirst();
    }

    @Override
    public Optional<Shipping> findByUpstreamExternalIdentifier(final String upstreamExternalIdentifier) {
        return template.query("SELECT * FROM shippings WHERE upstream_external_identifier = ?", shippingRowMapper, upstreamExternalIdentifier)
                .stream()
                .findFirst();
    }
}
