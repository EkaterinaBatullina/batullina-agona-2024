package org.example.dto.response;

import java.util.Set;

public record AuthorResponse(String name, AuthorProfileResponse profile, Set<BookResponse> books) {}
