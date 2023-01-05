package com.raphaelcollin.ordermanagement.infrastructure.persistence.document;

import com.raphaelcollin.ordermanagement.domain.entity.Payment;
import com.raphaelcollin.ordermanagement.domain.entity.Payment.PaymentStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Document
@Data
@AllArgsConstructor
public class PaymentDocument {
    String paymentMethod;
    PaymentStatus paymentStatus;
    LocalDateTime lastUpdated;

    public Payment toDomain() {
        return new Payment(paymentMethod, paymentStatus, lastUpdated);
    }

    public static PaymentDocument fromDomain(Payment payment) {
        return new PaymentDocument(
                payment.getPaymentMethod(),
                payment.getPaymentStatus(),
                payment.getLastUpdated()
        );
    }
}
