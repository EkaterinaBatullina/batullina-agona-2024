package org.example.controller;

import lombok.RequiredArgsConstructor;
import org.example.api.RoleApi;
import org.example.dto.request.RoleRequest;
import org.example.dto.response.RoleResponse;
import org.example.service.RoleServiceImpl;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.Set;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
public class RoleController implements RoleApi {

    private final RoleServiceImpl roleServiceImpl;

    @Override
    public RoleResponse getById(UUID uuid) {
        return roleServiceImpl.getById(uuid);
    }

    @Override
    public Set<RoleResponse> getAll() {
        return roleServiceImpl.getAll();
    }

    @Override
    public UUID create(RoleRequest roleRequest) {
        return roleServiceImpl.create(roleRequest);
    }

    @Override
    public ResponseEntity<Void> update(UUID uuid, RoleRequest roleRequest) {
        roleServiceImpl.update(uuid, roleRequest);
        return ResponseEntity.ok().build();
    }

    @Override
    public ResponseEntity<Void> delete(UUID uuid) {
        roleServiceImpl.delete(uuid);
        return ResponseEntity.noContent().build();
    }

    @Override
    public ResponseEntity<Void> patch(UUID uuid, RoleRequest roleRequest) {
        roleServiceImpl.patch(uuid, roleRequest);
        return ResponseEntity.ok().build();
    }

    @Override
    public Set<RoleResponse> getByName(String name) {
        return roleServiceImpl.getByName(name);
    }
}
