package org.example.service;

import org.example.dto.response.AuthorResponse;
import org.example.mapper.AuthorMapper;
import org.example.model.AuthorEntity;
import org.example.repository.AuthorRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@Service
public class AuthorServiceImpl implements AuthorService {
    private final AuthorRepository authorRepository;
    private final AuthorMapper authorMapper;

    public AuthorServiceImpl(AuthorRepository authorRepository, AuthorMapper authorMapper) {
        this.authorRepository = authorRepository;
        this.authorMapper = authorMapper;
    }
    @Override
    public Page<AuthorResponse> getAll(int page, int size, String sortBy) {
        Page<AuthorEntity> authorsPage = authorRepository.findAllWithBooksAndProfile(
                PageRequest.of(page, size, Sort.by(sortBy))
        );
        return authorsPage.map(authorMapper::toResponse);
    }
}
