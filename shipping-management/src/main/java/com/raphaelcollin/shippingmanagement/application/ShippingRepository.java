package com.raphaelcollin.shippingmanagement.application;

import com.raphaelcollin.shippingmanagement.domain.entity.Shipping;

import java.util.List;
import java.util.Optional;

public interface ShippingRepository {
    void save(Shipping shipping);
    void update(Shipping shipping);
    List<Shipping> findAll();
    Optional<Shipping> findById(int id);
    Optional<Shipping> findByUpstreamExternalIdentifier(String upstreamExternalIdentifier);
}
