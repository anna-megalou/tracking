package com.tracking.ubookit.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.tracking.ubookit.dto.Orders;
import com.tracking.ubookit.dto.OrdersResponse;
import com.tracking.ubookit.dto.TrackingResponse;
import com.tracking.ubookit.exception.OrderNotFoundException;
import com.tracking.ubookit.model.OrderDao;
import com.tracking.ubookit.repository.OrderRepository;

import lombok.RequiredArgsConstructor;
import com.tracking.ubookit.constant.CommonConstant;

/**
 * Service layer that contains the business logic for order tracking.
 * Communicates with the repository to fetch data and transforms
 * entities into DTOs suitable for the API response.
 */
@Service
@RequiredArgsConstructor
public class TrackingService {

    private final OrderRepository orderRepository;

    /**
     * Retrieves all orders from the database and maps them to summary DTOs.
     * Returns a wrapped response with status code and message.
     */
    public OrdersResponse getAllOrders() {
        List<OrderDao> results = orderRepository.findAll();
        List<Orders> orders = results.stream()
            .map(OrderDao::toDto)
            .collect(Collectors.toList());
        return OrdersResponse.builder()
            .code(CommonConstant.CODE_0)
            .message(CommonConstant.SUCCESS)
            .orders(orders)
            .build();
    }

    /**
     * Retrieves detailed tracking information for a specific order.
     * Throws OrderNotFoundException if the order ID does not exist.
     */
    public TrackingResponse getTracking(String orderId) {
        OrderDao order = orderRepository.findById(orderId)
                .orElseThrow(() -> new OrderNotFoundException(orderId));
        TrackingResponse response = order.toTrackingResponse();
        response.setCode(CommonConstant.CODE_0);
        response.setMessage(CommonConstant.SUCCESS);
        return response;
    }
}
