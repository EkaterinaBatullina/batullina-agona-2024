package org.example.repository;

import org.example.model.BookEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface BookRepository extends JpaRepository<BookEntity, UUID> {

    @Query("SELECT book FROM BookEntity book JOIN FETCH book.author")
    Page<BookEntity> findAllWithAuthor(Pageable pageable);
}
