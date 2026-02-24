package com.tracking.ubookit.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.tracking.ubookit.dto.OrdersResponse;
import com.tracking.ubookit.dto.TrackingResponse;
import com.tracking.ubookit.service.TrackingService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/orders")
@RequiredArgsConstructor
public class TrackingController {

    private final TrackingService trackingService;

    @GetMapping
    public ResponseEntity<OrdersResponse> getAllOrders() {
        OrdersResponse orders = trackingService.getAllOrders();
        return ResponseEntity.ok(orders);
    }

    @PostMapping("/{id}/tracking")
    public ResponseEntity<TrackingResponse> getTracking(@PathVariable String id) {
        TrackingResponse tracking = trackingService.getTracking(id);
        return ResponseEntity.ok(tracking);
    }
}
