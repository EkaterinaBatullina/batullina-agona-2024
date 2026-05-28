package com.technokratos.service;

import com.technokratos.event.OrderCreatedEvent;
import com.technokratos.event.PaymentRefundEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class PaymentServiceImpl implements PaymentService {
    private final PaymentEventProducer producer;
    private final PaymentMetricsService service;

    @Override
    public void create(OrderCreatedEvent event) {
        String orderId = event.orderId();
        boolean paymentSuccess = true;
        if (paymentSuccess) {
            service.incrementSuccess();
            log.info("Payment was successful for the order {}", orderId);
            producer.sendPaymentSucceededEvent(Long.valueOf(orderId));
        } else {
            service.incrementFailure();
            log.warn("Payment did not go through for the order {}", orderId);
            producer.sendPaymentFailedEvent(Long.valueOf(orderId));
        }
    }

    @Override
    public void refund(PaymentRefundEvent event) {
        log.info("Payment refund completed for order {}", event.orderId());
    }
}