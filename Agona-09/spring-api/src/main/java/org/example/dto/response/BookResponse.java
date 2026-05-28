package org.example.dto.response;

import java.util.UUID;

public record BookResponse(String title, UUID authorId) {}