package org.example.controller;

import org.example.dto.response.AuthorProfileResponse;
import org.example.service.AuthorProfileServiceImpl;
import org.example.api.AuthorProfileApi;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class AuthorProfileController implements AuthorProfileApi {

    private final AuthorProfileServiceImpl authorProfileService;

    @Override
    public Page<AuthorProfileResponse> getAll(int page, int size, String sortBy) {
        return authorProfileService.getAll(page, size, sortBy);
    }
}
