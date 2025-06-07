package com.technokratos.service;

import io.micrometer.core.instrument.MeterRegistry;
import io.micrometer.core.instrument.Counter;
import org.springframework.stereotype.Service;

@Service
public class PaymentMetricsService {
    private final MeterRegistry meterRegistry;
    private final Counter paymentSuccessCounter;
    private final Counter paymentFailureCounter;

    public PaymentMetricsService(MeterRegistry meterRegistry) {
        this.meterRegistry = meterRegistry;
        this.paymentSuccessCounter = meterRegistry.counter("payment.success.count");
        this.paymentFailureCounter = meterRegistry.counter("payment.failure.count");
    }

    public void incrementSuccess() {
        paymentSuccessCounter.increment();
    }

    public void incrementFailure() {
        paymentFailureCounter.increment();
    }
}