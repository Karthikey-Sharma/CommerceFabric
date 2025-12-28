package com.dailycodebuffer.productservice.service;

import com.dailycodebuffer.productservice.model.ProductRequest;

public interface ProductService {
    long addProduct(ProductRequest productRequest);
}
