package com.dailycodebuffer.productservice.model;

import lombok.Data;

@Data // used for getters and setters here
public class ProductRequest {
    private String name;
    private long price;
    private long quantity;
}
