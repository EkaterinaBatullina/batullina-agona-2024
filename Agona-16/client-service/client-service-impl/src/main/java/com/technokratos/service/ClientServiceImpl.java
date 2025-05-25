package com.technokratos.service;

import com.technokratos.dto.response.UserResponse;
import com.technokratos.feignClient.UserClient;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ClientServiceImpl {
    private final UserClient userClient;

    public UserResponse testFeignIntegration() {
        return userClient.getByUsername("username");
    }
}