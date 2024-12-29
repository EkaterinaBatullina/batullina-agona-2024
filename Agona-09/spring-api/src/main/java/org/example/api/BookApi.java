package org.example.api;

import org.example.dto.response.BookResponse;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/api/v1/books")
public interface BookApi {

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    Page<BookResponse> getAll(
            @RequestParam int page,
            @RequestParam int size,
            @RequestParam String sortBy);
}
