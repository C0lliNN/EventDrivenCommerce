package com.raphaelcollin.ordermanagement.infrastructure.persistence.document;

import com.raphaelcollin.ordermanagement.domain.entity.Customer;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
@Data
@AllArgsConstructor
public class CustomerDocument {
    String name;
    String email;
    String phone;

    public Customer toDomain() {
        return new Customer(name, email, phone);
    }

    public static CustomerDocument fromDomain(Customer customer) {
        return new CustomerDocument(
                customer.getName(),
                customer.getEmail(),
                customer.getPhone()
        );
    }
}
