package com.suyash586.rest.service.exception;

public class OfficeAlreadyExistsException extends RuntimeException {

    public OfficeAlreadyExistsException(final String message) {
        super(message);
    }
}
