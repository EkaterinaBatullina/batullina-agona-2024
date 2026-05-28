package org.example.service;

import lombok.RequiredArgsConstructor;
import org.example.dto.request.RoleRequest;
import org.example.dto.response.RoleResponse;
import org.example.exception.RoleNotFoundException;
import org.example.mapper.RoleMapper;
import org.example.model.RoleEntity;
import org.example.repository.RoleRepository;
import org.springframework.stereotype.Service;

import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class RoleServiceImpl implements RoleService {
    private final RoleRepository roleRepository;
    private final RoleMapper roleMapper;

    public RoleResponse getById(UUID uuid) {
        RoleEntity role = roleRepository.findById(uuid)
                .orElseThrow(() -> new RoleNotFoundException(uuid));
        return roleMapper.toResponse(role);
    }

    public Set<RoleResponse> getAll() {
        return roleRepository.findAll().stream()
                .map(roleMapper::toResponse)
                .collect(Collectors.toSet());
    }

    public UUID create(RoleRequest roleRequest) {
        RoleEntity role = roleMapper.toEntity(roleRequest);
        roleRepository.save(role);
        return role.getUuid();
    }

    public void delete(UUID uuid) {
        roleRepository.findById(uuid)
                .orElseThrow(() -> new RoleNotFoundException(uuid));
        roleRepository.deleteById(uuid);
    }

    public void update(UUID uuid, RoleRequest roleRequest) {
        RoleEntity roleEntity = roleRepository.findById(uuid)
                .orElseThrow(() -> new RoleNotFoundException(uuid));
        roleEntity = roleEntity.toBuilder()
                .name(roleRequest.name())
                .description(roleRequest.description())
                .build();
        roleRepository.save(roleEntity);
    }

    public void patch(UUID uuid, RoleRequest roleRequest) {
        RoleEntity roleEntity = roleRepository.findById(uuid)
                .orElseThrow(() -> new RoleNotFoundException(uuid));
        roleEntity = roleEntity.toBuilder()
                .name(roleRequest.name() != null ? roleRequest.name() : roleEntity.getName())
                .description(roleRequest.description() != null ? roleRequest.description() : roleEntity.getDescription())
                .build();
        roleRepository.save(roleEntity);
    }

    public Set<RoleResponse> getByName(String name) {
        return roleRepository.findByName(name).stream()
                .map(roleMapper::toResponse)
                .collect(Collectors.toSet());
    }
}
