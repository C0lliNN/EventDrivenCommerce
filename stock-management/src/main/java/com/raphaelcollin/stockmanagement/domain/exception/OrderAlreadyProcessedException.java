package com.raphaelcollin.stockmanagement.domain.exception;

public class OrderAlreadyProcessedException extends RuntimeException {
    public OrderAlreadyProcessedException(String message) {
        super(message);
    }
}
