package io.chatasticlogin.exception;

public class EntityNotFoundException extends RuntimeException {
    public EntityNotFoundException(String id) {
        super(String.format("Entity not found: %s", id));
    }
}
