package com.raphaelcollin.shippingmanagement.domain;

import com.raphaelcollin.shippingmanagement.domain.entity.Shipping;
import com.raphaelcollin.shippingmanagement.domain.entity.ShippingStatus;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ShippingTest {

    @ParameterizedTest
    @EnumSource(ShippingStatus.class)
    void isDelivered(ShippingStatus status) {
        Shipping shipping = Shipping.builder()
                .status(status)
                .build();

        assertEquals(status == ShippingStatus.DELIVERED, shipping.isDelivered());
    }
}