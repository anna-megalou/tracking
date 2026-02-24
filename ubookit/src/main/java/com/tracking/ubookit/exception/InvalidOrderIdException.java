package com.tracking.ubookit.exception;

/**
 * Thrown when the provided order ID does not match the expected format (e.g. "U" followed by digits).
 */
public class InvalidOrderIdException extends RuntimeException {

    public InvalidOrderIdException(String orderId) {
        super("Invalid order ID format: " + orderId + ". Expected format: U followed by digits (e.g. U44653)");
    }
}
