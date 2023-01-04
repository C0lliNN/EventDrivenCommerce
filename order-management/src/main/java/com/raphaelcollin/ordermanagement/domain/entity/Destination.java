package com.raphaelcollin.ordermanagement.domain.entity;

import lombok.Value;

@Value
public class Destination {
    String address;
    double latitude;
    double longitude;
    String instructions;
}
