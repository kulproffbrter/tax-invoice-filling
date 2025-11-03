package com.ez.taxform.service;

import java.util.Map;

public class ServiceValidationException extends RuntimeException {
    private static final long serialVersionUID = 1L;

    private final Map<String, String> errors;

    public ServiceValidationException(Map<String, String> errors) {
        super("Validation failed");
        this.errors = errors;
    }

    public Map<String, String> getErrors() {
        return errors;
    }
}
