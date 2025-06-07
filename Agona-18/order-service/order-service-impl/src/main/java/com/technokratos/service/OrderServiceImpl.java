package com.technokratos.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {

    @Override
    public void updateOrderStatus(String orderId, String status) {
        log.info("Order {} status updated to {}", orderId, status);
    }
}