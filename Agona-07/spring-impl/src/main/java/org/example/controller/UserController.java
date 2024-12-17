package org.example.controller;

import lombok.RequiredArgsConstructor;
import org.example.api.UserApi;
import org.example.dto.request.UserRequest;
import org.example.dto.response.UserResponse;
import org.example.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Set;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
public class UserController implements UserApi {

    private final UserService service;

    @Override
    public UserResponse getById(UUID uuid) {
        return service.getById(uuid);
    }

    @Override
    public Set<UserResponse> getAll() {
        return service.getAll();
    }

    @Override
    public UUID create(UserRequest userRequest) {
        return service.create(userRequest);
    }

    @Override
    public ResponseEntity<Void> update(UUID id, UserRequest userRequest) {
        service.update(id, userRequest);
        return ResponseEntity.ok().build();
    }

    @Override
    public ResponseEntity<Void> delete(UUID id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }

    @Override
    public ResponseEntity<Void> patch(UUID id, UserRequest userRequest) {
        service.patch(id, userRequest);
        return ResponseEntity.ok().build();
    }
}
