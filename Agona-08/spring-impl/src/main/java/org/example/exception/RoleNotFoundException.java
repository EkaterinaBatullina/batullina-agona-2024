package org.example.exception;

import java.util.UUID;

public class RoleNotFoundException extends NotFoundServiceException {

    public RoleNotFoundException(UUID uuid) {
        super("Role with id = '%s' - not found".formatted(uuid));
    }
}
