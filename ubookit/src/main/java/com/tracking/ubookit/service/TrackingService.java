package com.tracking.ubookit.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.tracking.ubookit.dto.Orders;
import com.tracking.ubookit.dto.OrdersResponse;
import com.tracking.ubookit.dto.TrackingResponse;
import com.tracking.ubookit.model.OrderDao;
import com.tracking.ubookit.repository.OrderRepository;

import lombok.RequiredArgsConstructor;
import com.tracking.ubookit.constant.CommonConstant;

@Service
@RequiredArgsConstructor
public class TrackingService {

    private final OrderRepository orderRepository;

    public OrdersResponse getAllOrders() {
        try {
            List<OrderDao> results = orderRepository.findAll();
            List<Orders> orders = results.stream()
                .map(OrderDao::toDto)
                .collect(Collectors.toList());
            return OrdersResponse.builder()
                .code(CommonConstant.CODE_0)
                .message(CommonConstant.SUCCESS)
                .orders(orders)
                .build();
        } catch (Exception e) {
            return OrdersResponse.builder()
                .code(CommonConstant.CODE_1)
                .message(CommonConstant.ERROR)
                .description(e.getMessage())
                .build();
        }
    }

    public TrackingResponse getTracking(String orderId) {
        try {
            OrderDao order = orderRepository.findById(orderId)
                    .orElseThrow(() -> new RuntimeException("Order not found: " + orderId));
            TrackingResponse response = order.toTrackingResponse();
            response.setCode(CommonConstant.CODE_0);
            response.setMessage(CommonConstant.SUCCESS);
            return response;
        } catch (Exception e) {
            return TrackingResponse.builder()
                .code(CommonConstant.CODE_1)
                .message(CommonConstant.ERROR)
                .description(e.getMessage())
                .build();
        }
    }
}
