package com.dailycodebuffer.orderservice.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


public enum PaymentMode {
    CASH,
    PAYPAL,
    DEBIT_CARD,
    CREDIT_CARD,
    APPLE_PAY
}
