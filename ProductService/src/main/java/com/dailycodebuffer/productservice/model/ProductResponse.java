package com.dailycodebuffer.productservice.model;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ProductResponse {
    private String productName;
    private long productId;
    private long quatity;
    private long price;
}
