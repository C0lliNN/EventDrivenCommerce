package com.raphaelcollin.ordermanagement.domain.event;

import com.raphaelcollin.ordermanagement.domain.entity.Customer;
import lombok.Value;

@Value
public class CustomerEvent {
    String name;
    String email;
    String phone;

    public static CustomerEvent fromEntity(Customer customer) {
        if (customer == null) {
            return null;
        }
        return new CustomerEvent(customer.getName(), customer.getEmail(), customer.getPhone());
    }

    public Customer toEntity() {
        return new Customer(name, email, phone);
    }
}
