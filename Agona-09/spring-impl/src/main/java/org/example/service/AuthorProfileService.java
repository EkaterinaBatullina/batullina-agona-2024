package org.example.service;

import org.example.dto.response.AuthorProfileResponse;
import org.springframework.data.domain.Page;

public interface AuthorProfileService {

    Page<AuthorProfileResponse> getAll(int page, int size, String sortBy);
}
