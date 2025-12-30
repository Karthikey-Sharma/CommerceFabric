package com.dailycodebuffer.cloudgateway.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class FallbackController {
    @GetMapping("/orderServiceFallBack")
    private String orderServiceFallback(){
        return "Order Service is down!";
    }

    @GetMapping("/paymentServiceFallBack")
    private String paymentServiceFallback(){
        return "Payment Service is down!";
    }

    @GetMapping("/productServiceFallBack")
    private String productServiceFallback(){
        return "Product Service is down!";
    }
}
