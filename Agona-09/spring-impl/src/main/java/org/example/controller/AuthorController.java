package org.example.controller;

import org.example.dto.response.AuthorResponse;
import org.example.service.AuthorServiceImpl;
import org.example.api.AuthorApi;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class AuthorController implements AuthorApi {

    private final AuthorServiceImpl authorService;

    @Override
    public Page<AuthorResponse> getAll(int page, int size, String sortBy) {
        return authorService.getAll(page, size, sortBy);
    }
}
