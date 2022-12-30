package com.raphaelcollin.inventorymanagement.domain.entity;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

class ProductTest {

    @Nested
    @DisplayName("method: getInventoryProducts")
    class GetInventoryProductsTest {

        @Test
        @DisplayName("when called, it should return a new Set instance with the same items")
        void whenCalled_shouldReturnNewSetInstanceWithTheSameItems() {
            InventoryProduct inventoryProduct1 = new InventoryProduct("inventory-id1", "product-id-1", 4);
            InventoryProduct inventoryProduct2 = new InventoryProduct("inventory-id2", "product-id-2", 5);

            Set<InventoryProduct> inventoryProducts = Set.of(inventoryProduct1, inventoryProduct2);

            Product product = Product.builder().inventoryProducts(inventoryProducts).build();

            assertThat(product.getInventoryProducts())
                    .isNotSameAs(inventoryProducts)
                    .containsExactlyElementsOf(inventoryProducts);
        }
    }

    @Nested
    @DisplayName("method: getPrice")
    class GetPriceTest {

        @Test
        @DisplayName("when called, it should the product price")
        void whenCalled_shouldReturnProductPrice() {
            Product product = Product.builder().price(14.5).build();

            assertThat(product.getPrice()).isEqualTo(14.5);
        }
    }

    @Nested
    @DisplayName("method: getTotalAmountAvailable")
    class GetTotalAmountAvailableTest {

        @Test
        @DisplayName("when InventoryProduct is null, it should return zero")
        void whenInventoryProductIsNull_shouldReturnZero() {
            Product product = Product.builder().build();

            assertThat(product.getTotalAmountAvailable()).isZero();
        }

        @Test
        @DisplayName("when InventoryProduct is not null, it should return the sum of all quantities")
        void whenInventoryProductIsNotNull_shouldReturnTheSumOfAllQuantities() {
            InventoryProduct inventoryProduct1 = new InventoryProduct("", "", 4);
            InventoryProduct inventoryProduct2 = new InventoryProduct("", "", 5);

            Product product = Product.builder()
                    .inventoryProducts(Set.of(inventoryProduct1, inventoryProduct2))
                    .build();

            assertThat(product.getTotalAmountAvailable()).isEqualTo(9);
        }
    }

    @Nested
    @DisplayName("method: isAvailable")
    class IsAvailableTest {

        @Test
        @DisplayName("when there is not any item available, then it should return false")
        void whenThereIsNotAnyItemAvailable_shouldReturnFalse() {
            InventoryProduct inventoryProduct1 = new InventoryProduct("inventory-1", "", 0);
            InventoryProduct inventoryProduct2 = new InventoryProduct("inventory-2", "", 0);

            Product product = Product.builder()
                    .inventoryProducts(Set.of(inventoryProduct1, inventoryProduct2))
                    .build();

            assertThat(product.isAvailable()).isFalse();
        }

        @Test
        @DisplayName("when there is some item available, then it should return true")
        void whenThereIsSomeItemAvailable_shouldReturnTrue() {
            InventoryProduct inventoryProduct1 = new InventoryProduct("", "", 4);
            InventoryProduct inventoryProduct2 = new InventoryProduct("", "", 3);

            Product product = Product.builder()
                    .inventoryProducts(Set.of(inventoryProduct1, inventoryProduct2))
                    .build();

            assertThat(product.isAvailable()).isTrue();
        }
    }
}