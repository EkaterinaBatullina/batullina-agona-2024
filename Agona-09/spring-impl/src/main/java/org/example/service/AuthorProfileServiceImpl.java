package org.example.service;

import org.example.dto.response.AuthorProfileResponse;
import org.example.mapper.AuthorProfileMapper;
import org.example.model.AuthorProfileEntity;
import org.example.repository.AuthorProfileRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@Service
public class AuthorProfileServiceImpl implements AuthorProfileService {
    private final AuthorProfileRepository authorProfileRepository;
    private final AuthorProfileMapper authorProfileMapper;

    public AuthorProfileServiceImpl(AuthorProfileRepository authorProfileRepository, AuthorProfileMapper authorProfileMapper) {
        this.authorProfileRepository = authorProfileRepository;
        this.authorProfileMapper = authorProfileMapper;
    }
    @Override
    public Page<AuthorProfileResponse> getAll(int page, int size, String sortBy) {
        Page<AuthorProfileEntity> profilesPage = authorProfileRepository.findAll(
                PageRequest.of(page, size, Sort.by(sortBy))
        );
        return profilesPage.map(authorProfileMapper::toResponse);
    }

}
