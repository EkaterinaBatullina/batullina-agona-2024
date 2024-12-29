package org.example.controller;

import org.example.dto.response.BookResponse;
import org.example.service.BookServiceImpl;
import org.example.api.BookApi;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class BookController implements BookApi {

    private final BookServiceImpl bookService;

    @Override
    public Page<BookResponse> getAll(int page, int size, String sortBy) {
        return bookService.getAll(page, size, sortBy);
    }
}
