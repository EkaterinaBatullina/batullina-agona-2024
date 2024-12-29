package org.example.dto.request;

import java.util.UUID;

public record BookRequest(String title, UUID authorId) {}
