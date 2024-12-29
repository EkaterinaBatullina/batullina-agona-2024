package org.example.service;

import org.example.dto.response.AuthorResponse;
import org.springframework.data.domain.Page;

public interface AuthorService {

    Page<AuthorResponse> getAll(int page, int size, String sortBy);
}