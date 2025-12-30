package com.dailycodebuffer.orderservice.service;

import com.dailycodebuffer.orderservice.model.OrderRequest;
import com.dailycodebuffer.orderservice.model.OrderResponse;

public interface OrderService {
    long placeOrder(OrderRequest orderRequest);

    OrderResponse getOrderDetails(Long orderId);
}
