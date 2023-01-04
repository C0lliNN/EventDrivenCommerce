package com.raphaelcollin.ordermanagement.application.ordermanager.response;

import com.raphaelcollin.ordermanagement.domain.entity.Customer;
import lombok.Value;

@Value
public class CustomerResponse {
    String name;
    String email;
    String phone;

    public static CustomerResponse fromDomain(Customer customer) {
        if (customer == null) {
            return null;
        }

        return new CustomerResponse(customer.getName(), customer.getEmail(), customer.getPhone());
    }
}
