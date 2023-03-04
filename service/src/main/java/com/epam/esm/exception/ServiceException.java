package com.epam.esm.exception;

import org.springframework.http.HttpStatus;

public class ServiceException extends RuntimeException {

    private final HttpStatus httpStatus;

    public ServiceException(String message, HttpStatus httpStatus) {
        super(message);
        this.httpStatus = httpStatus;
    }

    public ServiceException(String message, Throwable cause, HttpStatus httpStatus) {
        super(message, cause);
        this.httpStatus = httpStatus;
    }

    public HttpStatus getHttpStatus() {
        return httpStatus;
    }

}