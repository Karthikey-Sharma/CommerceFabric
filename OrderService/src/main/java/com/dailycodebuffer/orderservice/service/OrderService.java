package com.dailycodebuffer.orderservice.service;

import com.dailycodebuffer.orderservice.model.OrderRequest;

public interface OrderService {
    long placeOrder(OrderRequest orderRequest);
}
