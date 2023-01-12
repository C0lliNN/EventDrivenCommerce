package com.raphaelcollin.ordermanagement.domain.entity;

import org.assertj.core.data.TemporalUnitOffset;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;

import java.time.temporal.ChronoUnit;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.within;

class DeliveryTest {

    @ParameterizedTest
    @EnumSource(Delivery.DeliveryStatus.class)
    void testIsDelivered(Delivery.DeliveryStatus status) {
        Delivery delivery = new Delivery(status, null);
        assertThat(delivery.isDelivered()).isEqualTo(status == Delivery.DeliveryStatus.DELIVERED);
    }

    @Test
    void testUpdateStatus() {
        Delivery delivery = new Delivery(Delivery.DeliveryStatus.IN_INVENTORY, null);

        delivery.updateStatus(Delivery.DeliveryStatus.DELIVERED);

        assertThat(delivery.getDeliveryStatus()).isEqualTo(Delivery.DeliveryStatus.DELIVERED);
        assertThat(delivery.getLastUpdated()).isCloseToUtcNow(within(5, ChronoUnit.SECONDS));
    }
}