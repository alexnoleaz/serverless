package com.example.serverless.exception;

public class ValueAlreadyUsedException extends RuntimeException {
    private static final String DEFAULT_MESSAGE = "The value is already used.";

    private String property;
    private Object value;

    public ValueAlreadyUsedException() {
        super(DEFAULT_MESSAGE);
    }

    public ValueAlreadyUsedException(String message) {
        super(message);
    }

    public ValueAlreadyUsedException(String message, Throwable cause) {
        super(message, cause);
    }

    public ValueAlreadyUsedException(String property, Object value) {
        this(property, value, null);
    }

    public ValueAlreadyUsedException(String property, Object value, Throwable cause) {
        super(String.format("The value '%s' of property '%s' is already used.", value, property), cause);
        this.property = property;
        this.value = value;
    }

    public String getProperty() {
        return property;
    }

    public Object getValue() {
        return value;
    }
}
