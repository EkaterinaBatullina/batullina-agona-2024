package org.example.repository;

import org.example.model.AuthorProfileEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface AuthorProfileRepository extends JpaRepository<AuthorProfileEntity, UUID> {

    Page<AuthorProfileEntity> findAll(Pageable pageable);
}