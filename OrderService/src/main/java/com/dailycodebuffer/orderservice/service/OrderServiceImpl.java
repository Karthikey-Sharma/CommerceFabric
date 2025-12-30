package com.dailycodebuffer.orderservice.service;

import com.dailycodebuffer.orderservice.entity.Order;
import com.dailycodebuffer.orderservice.exception.CustomException;
import com.dailycodebuffer.orderservice.external.client.PaymentService;
import com.dailycodebuffer.orderservice.external.client.ProductService;
import com.dailycodebuffer.orderservice.external.request.PaymentRequest;
import com.dailycodebuffer.orderservice.external.response.PaymentResponse;
import com.dailycodebuffer.orderservice.model.OrderRequest;
import com.dailycodebuffer.orderservice.model.OrderResponse;
import com.dailycodebuffer.orderservice.model.ProductResponse;
import com.dailycodebuffer.orderservice.repository.OrderRepository;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.time.Instant;

@Service
@Log4j2
public class OrderServiceImpl implements OrderService{
    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private ProductService productService;

    @Autowired
    private PaymentService paymentService;

    @Autowired
    private RestTemplate restTemplate;

    @Override
    public long placeOrder(OrderRequest orderRequest) {
        // OrderEntity -> save the data with status order Created
        // Product Service -> Block Products (Reduce the Quantity)
        // PaymentService -? Complete the payment -> SUCESS -> complete or cancelled

        log.info("Placing Order request : {}" , orderRequest);
        productService.reduceQuantity(orderRequest.getProductId() , orderRequest.getQuantity()); // feign se kiya ye
        log.info("Created Order with status CREATED");
        Order order = Order.builder()
                .amount(orderRequest.getTotalAmount())
                .orderStatus("CREATED")
                .productId(orderRequest.getProductId())
                .orderDate(Instant.now())
                .quantity(orderRequest.getQuantity())
                .build();

        order = orderRepository.save(order);

        log.info("Calling Payment Service to comlete the payments");
        PaymentRequest paymentRequest = PaymentRequest.builder()
                .orderId(order.getId())
                .paymentMode(orderRequest.getPaymentMode())
                .amount(orderRequest.getTotalAmount())
                .build();
        String orderStatus = null;
                try{
                    paymentService.doPayment(paymentRequest);
                    log.info("Payment done Succesfully , changing the order status to SUCCESS");
                    orderStatus = "PLACED";
                }
                catch (Exception e){
                    log.error("Error occured in payment, Changing order status to PAYMENT FAILED");
                    orderStatus = "PAYMENT_FAILED";
                }
                order.setOrderStatus(orderStatus);
                orderRepository.save(order);
        log.info("Order Placed successfully with  Order Id: {}" , order.getId());
        return order.getId();

  }

    @Override
    public OrderResponse getOrderDetails(Long orderId) {
        log.info("GET order Details for order id : {}" , orderId);
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new CustomException(
                        "Order not found for the order Id : " + orderId,
                        "NOT_FOUND",
                        404
                ));

        log.info("Invoking Product service to fetch the product for id : "+ order.getProductId());
        ProductResponse productResponse =   restTemplate.getForObject("http://PRODUCT-SERVICE/product/" + order.getProductId(),
                ProductResponse.class);

        log.info("Getting payment information from payment Service");
        PaymentResponse paymentResponse = restTemplate.getForObject("http://PAYMENT-SERVICE/payment/order/" + order.getId(), PaymentResponse.class);
        OrderResponse.ProductDetails productDetails = OrderResponse.ProductDetails.builder()
                .productName(productResponse.getProductName())
                .productId(productResponse.getProductId())
                .build();


        OrderResponse.PaymentDetails  paymentDetails = OrderResponse.PaymentDetails.builder()
                .paymentId(paymentResponse.getPaymentId())
                .paymentStatus(paymentResponse.getStatus())
                .paymentDate(paymentResponse.getPaymentDate())
                .paymentMode(paymentResponse.getPaymentMode())
                .build();

        OrderResponse orderResponse = OrderResponse.builder()
                .orderId(order.getId())
                .orderStatus(order.getOrderStatus())
                .amount(order.getAmount())
                .orderDate(order.getOrderDate())
                .productDetails(productDetails)
                .paymentDetails(paymentDetails)
                .build();
        return orderResponse;
    }
}
