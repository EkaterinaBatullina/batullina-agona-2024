package com.technokratos.feignClient;

import com.technokratos.dto.request.UserFullRequest;
import com.technokratos.dto.request.RoleRequest;
import com.technokratos.dto.request.UserPartialRequest;
import com.technokratos.dto.response.StatisticResponse;
import com.technokratos.dto.response.UserResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;
import org.springframework.cloud.openfeign.SpringQueryMap;

import java.util.UUID;

@FeignClient(name = "user-service", path = "/api/v1/users")
public interface UserClient {

    @GetMapping("/me")
    UserResponse getMe();

    @GetMapping("/me/statistic")
    StatisticResponse getStatistic();

    @GetMapping("/{username}")
    UserResponse getByUsername(@PathVariable("username") String username);

    @GetMapping
    Page<UserResponse> getAll(@SpringQueryMap Pageable pageable);

    @PutMapping("/me")
    void updateMe(@RequestBody UserFullRequest userFullRequest);

    @DeleteMapping("/me")
    void delete();

    @PatchMapping("/me")
    void patch(@RequestBody UserPartialRequest userPartialRequest);

    @PatchMapping("/{id}/role")
    void updateRole(@PathVariable("id") UUID uuid, @RequestBody RoleRequest roleRequest);
}