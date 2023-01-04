package com.raphaelcollin.ordermanagement.domain.entity;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class ItemTest {

    @Test
    void getTotalPrice() {
        Item item = Item.builder().quantity(3).price(4.25).build();

        assertThat(item.getTotalPrice()).isEqualTo(12.75);
    }

    @Nested
    @DisplayName("method: isQuantityAvailable")
    class IsQuantityAvailableMethod {

        @Test
        @DisplayName("when item total quantity is less than the requested quantity, it should return false")
        void whenItemTotalQuantityIsLessThanTheRequestQuantity_shouldReturnFalse() {
            Item item = Item.builder().quantity(4).build();

            assertThat(item.isQuantityAvailable(5)).isFalse();
        }

        @Test
        @DisplayName("when item total quantity is equal to the requested quantity, it should return false")
        void whenItemTotalQuantityIsEqualToTheRequestQuantity_shouldReturnTrue() {
            Item item = Item.builder().quantity(4).build();

            assertThat(item.isQuantityAvailable(4)).isTrue();
        }

        @Test
        @DisplayName("when item total quantity is greater than the requested quantity, it should return false")
        void whenItemTotalQuantityIsGreaterThanTheRequestQuantity_shouldReturnTrue() {
            Item item = Item.builder().quantity(4).build();

            assertThat(item.isQuantityAvailable(3)).isTrue();
        }
    }
}