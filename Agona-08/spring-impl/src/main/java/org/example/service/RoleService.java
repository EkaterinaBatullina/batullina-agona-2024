package org.example.service;

import org.example.dto.request.RoleRequest;
import org.example.dto.response.RoleResponse;

import java.util.Set;
import java.util.UUID;

public interface RoleService {

    RoleResponse getById(UUID uuid);

    Set<RoleResponse> getAll();

    UUID create(RoleRequest roleRequest);

    void update(UUID uuid, RoleRequest roleRequest);

    void delete(UUID uuid);

    void patch(UUID uuid, RoleRequest roleRequest);

    Set<RoleResponse> getByName(String name);
}
