package com.raphaelcollin.inventorymanagement.domain.entity;

import com.raphaelcollin.inventorymanagement.domain.exception.InvalidQuantityException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class ProductTest {

    @Nested
    @DisplayName("method: getInventoryProducts")
    class GetInventoryProductsTest {

        @Test
        @DisplayName("when inventoryProducts is null, then it should return an empty set")
        void whenInventoryProductsIsNull_shouldReturnAnEmptySet() {
            Product product = Product.builder().build();

            assertThat(product.getInventoryProducts()).isEmpty();
        }

        @Test
        @DisplayName("when inventoryProducts is not null, it should return a new Set instance with the same items")
        void whenInventoryProductsIsNotNull_shouldReturnNewSetInstanceWithTheSameItems() {
            InventoryProduct inventoryProduct1 = new InventoryProduct("inventory-id1", "product-id-1", 4);
            InventoryProduct inventoryProduct2 = new InventoryProduct("inventory-id2", "product-id-2", 5);

            Set<InventoryProduct> inventoryProducts = Set.of(inventoryProduct1, inventoryProduct2);

            Product product = Product.builder().inventoryProducts(inventoryProducts).build();

            assertThat(product.getInventoryProducts())
                    .isNotSameAs(inventoryProducts)
                    .containsExactlyInAnyOrderElementsOf(inventoryProducts);
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

    @Nested
    @DisplayName("method: addInventoryProduct")
    class AddInventoryProductTest {

        @Test
        @DisplayName("when inventoryProducts is null, a single item should be present at the inventoryProducts set")
        void whenInventoryProductsIsNull_singleItemShouldBePresentAtTheInventoryProductsSet() {
            InventoryProduct inventoryProduct = new InventoryProduct("", "", 4);
            Product product = Product.builder().build();

            product.addInventoryProduct(inventoryProduct);

            assertThat(product.getInventoryProducts()).containsExactly(inventoryProduct);
        }

        @Test
        @DisplayName("when inventoryProducts is not null, a new item should be added to the inventoryProducts set")
        void whenInventoryProductsIsNotNull_newItemShouldBeAddedToTheInventoryProductsSet() {
            InventoryProduct inventoryProduct1 = new InventoryProduct("", "", 4);
            InventoryProduct inventoryProduct2 = new InventoryProduct("", "", 5);
            Product product = Product.builder().build();

            product.addInventoryProduct(inventoryProduct1);
            product.addInventoryProduct(inventoryProduct2);

            assertThat(product.getInventoryProducts()).hasSize(2).containsExactlyInAnyOrder(inventoryProduct1, inventoryProduct2);
        }
    }

    @Nested
    @DisplayName("method: applyQuantityChange(int)")
    class ApplyQuantityChangeMethodTest {

        @Test
        @DisplayName("when quantity is positive or zero, then it should increment to some inventory quantity")
        void whenQuantityIsPositiveOrZero_shouldIncrementToSomeInventoryQuantity() {
            InventoryProduct inventoryProduct1 = new InventoryProduct("", "", 4);
            InventoryProduct inventoryProduct2 = new InventoryProduct("", "", 5);

            Product product = Product.builder()
                    .inventoryProducts(Set.of(inventoryProduct1, inventoryProduct2))
                    .build();

            product.applyQuantityChange(8);

            assertThat(product.getTotalAmountAvailable()).isEqualTo(17);
        }

        @Test
        @DisplayName("when quantity is negative and it's greater than the total amount, then it should throw an exception")
        void whenQuantityIsNegativeAndItsGreaterThanTheTotalAmount_shouldThrowAnException() {
            InventoryProduct inventoryProduct1 = new InventoryProduct("", "", 4);
            InventoryProduct inventoryProduct2 = new InventoryProduct("", "", 5);

            Product product = Product.builder()
                    .inventoryProducts(Set.of(inventoryProduct1, inventoryProduct2))
                    .build();

            assertThatThrownBy(() -> product.applyQuantityChange(-10))
                    .isInstanceOf(InvalidQuantityException.class);
        }

        @Test
        @DisplayName("when quantity is negative and it's lower or equal to the total amount, then it should apply correctly")
        void whenQuantityIsNegativeAndItsLowerOrEqualToTheTotalAmount_shouldApplyCorrectly() {
            InventoryProduct inventoryProduct1 = new InventoryProduct("", "", 4);
            InventoryProduct inventoryProduct2 = new InventoryProduct("", "", 5);

            Product product = Product.builder()
                    .inventoryProducts(Set.of(inventoryProduct1, inventoryProduct2))
                    .build();

            product.applyQuantityChange(-6);
            assertThat(product.getTotalAmountAvailable()).isEqualTo(3);

            product.applyQuantityChange(-3);
            assertThat(product.getTotalAmountAvailable()).isEqualTo(0);
        }
    }
}