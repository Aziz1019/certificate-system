package com.epam.esm.exception;

public class ServiceException extends Exception {
    public ServiceException(String message, Throwable throwable) {
        super(message, throwable);
    }

}