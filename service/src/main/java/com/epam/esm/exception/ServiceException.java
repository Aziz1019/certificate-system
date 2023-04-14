package com.epam.esm.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class ServiceException extends Exception {
    private Object[] violations;

    public ServiceException(String message, Throwable throwable) {
        super(message, throwable);
    }

    public ServiceException(Object[] violations, String message) {
        super(message);
        this.violations = violations;
    }

    public Object[] getViolations() {
        return violations;
    }
}