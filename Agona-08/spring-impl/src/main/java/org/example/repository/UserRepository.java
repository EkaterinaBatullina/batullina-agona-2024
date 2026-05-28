package org.example.repository;

import org.example.model.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, UUID> {

    @Query("SELECT u FROM UserEntity u WHERE u.name = :name")
    List<UserEntity> findByName(@Param("name") String name);

    @Query("SELECT u FROM UserEntity u LEFT JOIN u.roles r")
    List<UserEntity> findAllWithRoles();

    @Query("SELECT u FROM UserEntity u INNER JOIN u.roles r WHERE r.name = :roleName")
    List<UserEntity> findByRole(@Param("roleName") String roleName);
}