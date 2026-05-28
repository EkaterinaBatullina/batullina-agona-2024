package com.technokratos.exception.type;

import java.util.UUID;

public class UserNotFoundException extends NotFoundServiceException {

    public UserNotFoundException(UUID uuid) {
        super("User with id = '%s' - not found".formatted(uuid));
    }
}