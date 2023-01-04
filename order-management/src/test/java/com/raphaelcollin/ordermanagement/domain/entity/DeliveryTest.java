package com.raphaelcollin.ordermanagement.domain.entity;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;

import static org.assertj.core.api.Assertions.assertThat;

class DeliveryTest {

    @ParameterizedTest
    @EnumSource(Delivery.DeliveryStatus.class)
    void testIsDelivered(Delivery.DeliveryStatus status) {
        Delivery delivery = new Delivery(status, null);
        assertThat(delivery.isDelivered()).isEqualTo(status == Delivery.DeliveryStatus.DELIVERED);
    }

}