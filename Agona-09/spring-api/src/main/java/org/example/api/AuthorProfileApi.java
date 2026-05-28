package org.example.api;

import org.example.dto.response.AuthorProfileResponse;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;


@RequestMapping("/api/v1/author-profiles")
public interface AuthorProfileApi {

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    Page<AuthorProfileResponse> getAll(
            @RequestParam int page,
            @RequestParam int size,
            @RequestParam String sortBy
    );
}
