package org.example.service;

import org.example.dto.response.BookResponse;
import org.springframework.data.domain.Page;

public interface BookService {

    Page<BookResponse> getAll(int page, int size, String sortBy);
}