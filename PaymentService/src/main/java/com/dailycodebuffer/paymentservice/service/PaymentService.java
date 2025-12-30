package com.dailycodebuffer.paymentservice.service;

import com.dailycodebuffer.paymentservice.model.PaymentRequest;

public interface PaymentService {
    Long doPayment(PaymentRequest paymentRequest);
}
