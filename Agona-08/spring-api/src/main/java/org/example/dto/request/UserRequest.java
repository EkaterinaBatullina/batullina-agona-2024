package org.example.dto.request;

import java.util.Set;

public record UserRequest(String name, String email, Set<RoleRequest> roles) { }
