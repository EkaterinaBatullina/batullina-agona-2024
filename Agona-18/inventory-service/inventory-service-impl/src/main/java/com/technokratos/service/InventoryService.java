package com.technokratos.service;

import com.technokratos.event.PaymentSucceededEvent;

public interface InventoryService {

    void create(PaymentSucceededEvent event);
}