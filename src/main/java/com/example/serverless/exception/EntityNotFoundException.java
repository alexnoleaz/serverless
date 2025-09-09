package com.example.serverless.exception;

public class EntityNotFoundException extends RuntimeException {
    private static final String DEFAULT_MESSAGE = "The requested entity was not found.";

    private Class<?> entityClass;
    private Object id;

    public EntityNotFoundException() {
        super(DEFAULT_MESSAGE);
    }

    public EntityNotFoundException(String message) {
        super(message);
    }

    public EntityNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

    public EntityNotFoundException(Class<?> entityClass, Object id) {
        this(entityClass, id, null);
    }

    public EntityNotFoundException(Class<?> entityClass, Object id, Throwable cause) {
        super(String.format("Entity of class '%s' with ID '%s' was not found.", entityClass.getSimpleName(), id),
                cause);
        this.entityClass = entityClass;
        this.id = id;
    }

    public Class<?> getEntityClass() {
        return entityClass;
    }

    public Object getId() {
        return id;
    }
}
