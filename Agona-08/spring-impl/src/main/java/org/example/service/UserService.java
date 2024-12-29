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

    void update(UUID uuid, UserRequest userRequest);

    void patch(UUID uuid, UserRequest userRequest);

    Set<UserResponse> getByName(String name);

    Set<UserResponse> getAllWithRoles();

    Set<UserResponse> getByRole(String roleName);
}

