package com.raphaelcollin.ordermanagement.domain.entity;

import lombok.Value;

@Value
public class Customer {
    String name;
    String email;
    String phone;
}
