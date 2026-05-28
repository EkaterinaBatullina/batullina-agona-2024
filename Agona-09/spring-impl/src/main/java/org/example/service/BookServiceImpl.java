package org.example.service;

import org.example.dto.response.BookResponse;
import org.example.mapper.BookMapper;
import org.example.model.BookEntity;
import org.example.repository.BookRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@Service
public class BookServiceImpl implements BookService {
    private final BookRepository bookRepository;
    private final BookMapper bookMapper;

    public BookServiceImpl(BookRepository bookRepository, BookMapper bookMapper) {
        this.bookRepository = bookRepository;
        this.bookMapper = bookMapper;
    }
    @Override
    public Page<BookResponse> getAll(int page, int size, String sortBy) {
        Page<BookEntity> booksPage = bookRepository.findAllWithAuthor(
                PageRequest.of(page, size, Sort.by(sortBy)));
        return booksPage.map(bookMapper::toResponse);
    }
}
