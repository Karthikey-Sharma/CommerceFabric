package com.dailycodebuffer.orderservice.external.client;

import com.dailycodebuffer.orderservice.exception.CustomException;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;
@CircuitBreaker(name = "external" , fallbackMethod = "fallback")
@FeignClient(name = "PRODUCT-SERVICE/product")
public interface ProductService {
    @PutMapping("/reduceQuantity/{id}")   // just declaration daalna hota
    ResponseEntity<Void> reduceQuantity(
            @PathVariable("id") Long productId, @RequestParam Long quantity);

    default void fallback(Exception e){
        throw new CustomException("Product Service  in not available",
                "UNAVAILABLE",
                500);
    }
}

