package org.example.service;

import lombok.RequiredArgsConstructor;
import org.example.dto.request.UserRequest;
import org.example.dto.response.UserResponse;
import org.example.exception.UserNotFoundException;
import org.example.mapper.RoleMapper;
import org.example.mapper.UserMapper;
import org.example.model.RoleEntity;
import org.example.model.UserEntity;
import org.example.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BaseUserService implements UserService {
    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final RoleMapper roleMapper;


    @Override
    public UserResponse getById(UUID id) {
        UserEntity userEntity = userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException(id));
        return userMapper.toResponse(userEntity);
    }

    @Override
    public Set<UserResponse> getAll() {
        return userRepository.findAll().stream()
                .map(userMapper::toResponse)
                .collect(Collectors.toSet());
    }

    @Override
    public UUID create(UserRequest userRequest) {
        UserEntity userEntity = userMapper.toEntity(userRequest);
        userRepository.save(userEntity);
        return userEntity.getUuid();
    }

    @Override
    public void delete(UUID id) {
        userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException(id));
        userRepository.deleteById(id);
    }

    @Override
    public void update(UUID id, UserRequest userRequest) {
        UserEntity userEntity = userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException(id));
        Set<RoleEntity> roleEntities = userRequest.roles().stream()
                .map(roleRequest -> roleMapper.toEntity(roleRequest))
                .collect(Collectors.toSet());
        userEntity = userEntity.toBuilder()
                .name(userRequest.name())
                .email(userRequest.email())
                .roles(roleEntities)
                .build();
        userRepository.save(userEntity);
    }

    @Override
    public void patch(UUID id, UserRequest userRequest) {
        UserEntity userEntity = userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException(id));
        Set<RoleEntity> roleEntities = userRequest.roles() != null
                ? userRequest.roles().stream()
                .map(roleRequest -> roleMapper.toEntity(roleRequest))
                .collect(Collectors.toSet())
                : userEntity.getRoles();
        userEntity = userEntity.toBuilder()
                .name(userRequest.name() != null ? userRequest.name() : userEntity.getName())
                .email(userRequest.email() != null ? userRequest.email() : userEntity.getEmail())
                .roles(roleEntities)
                .build();
        userRepository.save(userEntity);
    }

    @Override
    public Set<UserResponse> getByName(String name) {
        return userRepository.findByName(name).stream()
                .map(userMapper::toResponse)
                .collect(Collectors.toSet());
    }

    @Override
    public Set<UserResponse> getAllWithRoles() {
        return userRepository.findAllWithRoles().stream()
                .map(userMapper::toResponse)
                .collect(Collectors.toSet());
    }

    @Override
    public Set<UserResponse> getByRole(String roleName) {
        return userRepository.findByRole(roleName).stream()
                .map(userMapper::toResponse)
                .collect(Collectors.toSet());
    }
}
