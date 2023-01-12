package com.raphaelcollin.ordermanagement.domain.entity;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.time.temporal.ChronoUnit;
import java.util.Collections;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.within;

class OrderTest {

    @Nested
    @DisplayName("method: getTotal")
    class GetTotalMethod {

        @Test
        @DisplayName("when items is null, then it should return zero")
        void whenItemsIsNull_shouldReturnZero() {
            Order order = Order.builder().build();
            assertThat(order.getTotal()).isZero();
        }

        @Test
        @DisplayName("when items is empty, then it should return zero")
        void whenItemsIsEmpty_shouldReturnZero() {
            Order order = Order.builder().items(Collections.emptySet()).build();
            assertThat(order.getTotal()).isZero();
        }

        @Test
        @DisplayName("when items is not empty, then it should return the sum of the items totalPrice")
        void whenItemsIsNotEmpty_shouldReturnTheSumOfTheItemsTotalPrice() {
            Item item1 = Item.builder().price(25.0).quantity(2).build();
            Item item2 = Item.builder().price(45.5).quantity(1).build();

            Order order = Order.builder().items(Set.of(item1, item2)).build();

            assertThat(order.getTotal()).isEqualTo(95.5);
        }
    }

    @Nested
    @DisplayName("method: isPaid")
    class IsPaidMethod {

        @Test
        @DisplayName("when payment is null, then it should return false")
        void whenPaymentIsNull_shouldReturnFalse() {
            Order order = Order.builder().build();

            assertThat(order.isPaid()).isFalse();
        }

        @Test
        @DisplayName("when payment status is not PAID, then it should return false")
        void whenPaymentStatusIsNotPaid_shouldReturnFalse() {
            Payment payment = new Payment(null, Payment.PaymentStatus.PENDING, null);
            Order order = Order.builder().payment(payment).build();

            assertThat(order.isPaid()).isFalse();
        }


        @Test
        @DisplayName("when payment status is PAID, then it should return false")
        void whenPaymentStatusIsPaid_shouldReturnTrue() {
            Payment payment = new Payment(null, Payment.PaymentStatus.PAID, null);
            Order order = Order.builder().payment(payment).build();

            assertThat(order.isPaid()).isTrue();
        }
    }

    @Nested
    @DisplayName("method: isDelivered")
    class IsDeliveredMethod {

        @Test
        @DisplayName("when delivery is null, then it should return false")
        void whenDeliveryIsNull_shouldReturnFalse() {
            Order order = Order.builder().build();

            assertThat(order.isDelivered()).isFalse();
        }

        @Test
        @DisplayName("when delivery status is not DELIVERED, then it should return false")
        void whenDeliveryStatusIsNotDelivered_shouldReturnFalse() {
            Delivery delivery = new Delivery(Delivery.DeliveryStatus.IN_INVENTORY, null);
            Order order = Order.builder().delivery(delivery).build();

            assertThat(order.isDelivered()).isFalse();
        }

        @Test
        @DisplayName("when delivery status is DELIVERED, then it should return true")
        void whenDeliveryStatusIsDelivered_shouldReturnTrue() {
            Delivery delivery = new Delivery(Delivery.DeliveryStatus.DELIVERED, null);
            Order order = Order.builder().delivery(delivery).build();

            assertThat(order.isDelivered()).isTrue();
        }
    }

    @Nested
    @DisplayName("method: getItems")
    class GetItemsMethod {

        @Test
        @DisplayName("when items is null, then it should return an empty set")
        void whenItemsIsNull_shouldReturnAnEmptySet() {
            Order order = Order.builder().items(null).build();

            assertThat(order.getItems()).isEmpty();
        }

        @Test
        @DisplayName("when items is not null, then it should return a new set instance with the same items")
        void whenItemsIsNotNull_shouldReturnANewSetInstanceWithTheSameItems() {
            Item item1 = Item.builder().id("id1").build();
            Item item2 = Item.builder().id("id2").build();
            Set<Item> items = Set.of(item1, item2);

            Order order = Order.builder().items(items).build();

            assertThat(order.getItems())
                    .isNotSameAs(items)
                    .containsExactlyInAnyOrderElementsOf(items);
        }
    }

    @Nested
    @DisplayName("method: updateDeliveryStatus(DeliveryStatus)")
    class UpdateDeliveryStatusMethod {

        @Test
        @DisplayName("when called, it should update the status and lastUpdated")
        void whenCalled_shouldUpdateTheStatusAndLastUpdated() {
            Order order = Order.builder()
                    .delivery(new Delivery(Delivery.DeliveryStatus.IN_INVENTORY, null))
                    .build();

            order.updateDeliveryStatus(Delivery.DeliveryStatus.IN_FLIGHT);

            assertThat(order.getDelivery().getDeliveryStatus()).isEqualTo(Delivery.DeliveryStatus.IN_FLIGHT);
            assertThat(order.getDelivery().getLastUpdated()).isCloseToUtcNow(within(5, ChronoUnit.SECONDS));
        }
    }
}