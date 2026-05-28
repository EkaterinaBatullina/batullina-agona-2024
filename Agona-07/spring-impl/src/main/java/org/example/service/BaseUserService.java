package org.example.service;

import lombok.RequiredArgsConstructor;
import org.example.dto.request.UserRequest;
import org.example.dto.response.UserResponse;
import org.example.exception.UserNotFoundException;
import org.example.mapper.UserMapper;
import org.example.model.UserEntity;
import org.example.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BaseUserService implements UserService {

    private final UserRepository repository;

    private final UserMapper mapper;

    @Override
    public UserResponse getById(UUID uuid) {
        return mapper.toResponse(
                repository.findById(uuid)
                        .orElseThrow(() -> new UserNotFoundException(uuid))
        );
    }

    @Override
    public Set<UserResponse> getAll() {
        return repository.findAll().stream()
                .map(mapper::toResponse)
                .collect(Collectors.toSet());
    }

    @Override
    public UUID create(UserRequest userRequest) {
        UserEntity userEntity = mapper.toEntity(userRequest);
        repository.save(userEntity);
        return userEntity.getUuid();
    }

    @Override
    public void delete(UUID uuid) {
        repository.deleteById(uuid);
    }

    @Override
    public void update(UUID uuid, UserRequest userRequest) {
        UserEntity userEntity = repository.findById(uuid)
                .orElseThrow(() -> new UserNotFoundException(uuid));
        userEntity = userEntity.builder()
                .name(userRequest.name())
                .phone(userRequest.phone())
                .build();
        repository.update(userEntity);
    }

    @Override
    public void patch(UUID uuid, UserRequest userRequest) {
        UserEntity userEntity = repository.findById(uuid)
                .orElseThrow(() -> new UserNotFoundException(uuid));
        userEntity = userEntity.builder()
                .name(userRequest.name() != null ? userRequest.name() : userEntity.getName())
                .phone(userRequest.phone() != null ? userRequest.phone() : userEntity.getPhone())
                .build();
        repository.update(userEntity);
    }
}

