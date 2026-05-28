package com.technokratos.service;

import com.technokratos.event.OrderCreatedEvent;
import com.technokratos.event.PaymentRefundEvent;

public interface PaymentService {

    void create(OrderCreatedEvent event);

    void refund(PaymentRefundEvent event);
}