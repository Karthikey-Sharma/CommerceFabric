package com.dailycodebuffer.orderservice.service;

import com.dailycodebuffer.orderservice.entity.Order;
import com.dailycodebuffer.orderservice.model.OrderRequest;
import com.dailycodebuffer.orderservice.repository.OrderRepository;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Instant;

@Service
@Log4j2
public class OrderServiceImpl implements OrderService{
    @Autowired
    private OrderRepository orderRepository;
    @Override
    public long placeOrder(OrderRequest orderRequest) {
        // OrderEntity -> save the data with status order Created
        // Product Service -> Block Products (Reduce the Quantity)
        // PaymentService -? Complete the payment -> SUCESS -> complete or cancelled

        log.info("Placing Order request : {}" , orderRequest);
        Order order = Order.builder()
                .amount(orderRequest.getTotalAmount())
                .orderStatus("CREATED")
                .productId(orderRequest.getProductId())
                .orderDate(Instant.now())
                .quantity(orderRequest.getQuantity())
                .build();

        order = orderRepository.save(order);
        log.info("Order Placed successfully with  Order Id: {}" , order.getId());
        return order.getId();

  }
}
