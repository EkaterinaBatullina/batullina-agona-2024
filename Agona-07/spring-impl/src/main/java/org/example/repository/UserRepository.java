package org.example.repository;

import org.example.model.UserEntity;

import java.util.Optional;
import java.util.Set;
import java.util.UUID;

public interface UserRepository {

    Optional<UserEntity> findById(UUID uuid);

    Set<UserEntity> findAll();

    void save(UserEntity userEntity);

    void update(UserEntity userEntity);

    void deleteById(UUID uuid);
}
