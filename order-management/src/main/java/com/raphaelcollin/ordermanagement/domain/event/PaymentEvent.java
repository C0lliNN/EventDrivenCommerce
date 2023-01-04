package com.raphaelcollin.ordermanagement.domain.event;

import com.raphaelcollin.ordermanagement.domain.entity.Payment;
import lombok.Value;

import java.time.LocalDateTime;

@Value
public class PaymentEvent {
    String paymentMethod;
    Payment.PaymentStatus paymentStatus;
    LocalDateTime lastUpdated;
    boolean paid;

    public static PaymentEvent fromEntity(Payment payment) {
        if (payment == null) {
            return null;
        }

        return new PaymentEvent(
                payment.getPaymentMethod(),
                payment.getPaymentStatus(),
                payment.getLastUpdated(),
                payment.isPaid()
        );
    }
}
