package org.example.dto.response;

import java.util.Set;

public record UserResponse(String name, String email, Set<RoleResponse> roles) { }

