package com.raphaelcollin.ordermanagement.domain.entity;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;

class PaymentTest {

    @ParameterizedTest
    @EnumSource(Payment.PaymentStatus.class)
    void testIsPaid(Payment.PaymentStatus status) {
        Payment payment = new Payment(null, status, null);

        Assertions.assertThat(payment.isPaid()).isEqualTo(status == Payment.PaymentStatus.PAID);
    }
}