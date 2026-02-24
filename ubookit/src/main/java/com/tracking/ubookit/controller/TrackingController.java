package com.tracking.ubookit.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.tracking.ubookit.dto.OrdersResponse;
import com.tracking.ubookit.dto.TrackingResponse;
import com.tracking.ubookit.service.TrackingService;

import lombok.RequiredArgsConstructor;

/**
 * REST controller that exposes the order tracking API.
 * Base path: /api/orders
 */
@RestController
@RequestMapping("/api/orders")
@RequiredArgsConstructor
public class TrackingController {

    private static final String ORDER_ID_PATTERN = "^U\\d+$";

    private final TrackingService trackingService;

    /** Returns a summary list of all orders in the system. */
    @GetMapping
    public ResponseEntity<OrdersResponse> getAllOrders() {
        OrdersResponse orders = trackingService.getAllOrders();
        return ResponseEntity.ok(orders);
    }

    /**
     * Returns detailed tracking information for a specific order.
     * The order ID must match the pattern "U" followed by digits (e.g. U44653).
     */
    @GetMapping("/{id}/tracking")
    public ResponseEntity<TrackingResponse> getTracking(@PathVariable String id) {
        TrackingResponse tracking = trackingService.getTracking(id);
        return ResponseEntity.ok(tracking);
    }
}
