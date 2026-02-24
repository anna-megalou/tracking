package com.tracking.ubookit.exception;

/**
 * Thrown when an order with the given ID does not exist in the database.
 */
public class OrderNotFoundException extends RuntimeException {

    public OrderNotFoundException(String orderId) {
        super("Order not found: " + orderId);
    }
}
