package org.example.repository;

import org.example.model.AuthorEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;

import java.util.UUID;

public interface AuthorRepository extends JpaRepository<AuthorEntity, UUID> {

    @Query("SELECT author FROM AuthorEntity author LEFT JOIN FETCH author.books LEFT JOIN FETCH author.profile")
    Page<AuthorEntity> findAllWithBooksAndProfile(Pageable pageable);
}