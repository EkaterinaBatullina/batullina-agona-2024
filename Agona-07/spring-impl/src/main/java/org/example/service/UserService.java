package org.example.service;

import org.example.dto.request.UserRequest;
import org.example.dto.response.UserResponse;

import java.util.Set;
import java.util.UUID;

public interface UserService {

    UserResponse getById(UUID uuid);

    Set<UserResponse> getAll();

    UUID create(UserRequest userRequest);

    void delete(UUID uuid);

    UserResponse update(UUID uuid, UserRequest userRequest);

    UserResponse patch(UUID uuid, UserRequest userRequest);
}
