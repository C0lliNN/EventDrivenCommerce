package com.raphaelcollin.ordermanagement.application.ordermanager.response;

import com.raphaelcollin.ordermanagement.domain.entity.Payment;
import lombok.Value;

import java.time.LocalDateTime;

@Value
public class PaymentResponse {
    String paymentMethod;
    Payment.PaymentStatus paymentStatus;
    LocalDateTime lastUpdated;
    boolean paid;

    public static PaymentResponse fromDomain(Payment payment) {
        if (payment == null) {
            return null;
        }

        return new PaymentResponse(
                payment.getPaymentMethod(),
                payment.getPaymentStatus(),
                payment.getLastUpdated(),
                payment.isPaid()
        );
    }
}
