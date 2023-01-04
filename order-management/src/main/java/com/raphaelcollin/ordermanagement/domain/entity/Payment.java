package com.raphaelcollin.ordermanagement.domain.entity;

import lombok.Value;

import java.time.LocalDateTime;

@Value
public class Payment {
    String paymentMethod;
    PaymentStatus paymentStatus;
    LocalDateTime lastUpdated;

    public enum PaymentStatus {
        PENDING,
        PAID,
        CANCELLED
    }

    boolean isPaid() {
        return paymentStatus == PaymentStatus.PAID;
    }
}
