package org.example.api;

import org.example.dto.response.AuthorResponse;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/api/v1/authors")
public interface AuthorApi {

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    Page<AuthorResponse> getAll(
            @RequestParam int page,
            @RequestParam int size,
            @RequestParam String sortBy);
}


