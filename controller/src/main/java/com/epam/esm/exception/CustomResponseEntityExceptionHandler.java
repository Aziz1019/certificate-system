package com.epam.esm.exception;

import com.epam.esm.responseMessage.ResMessage;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class CustomResponseEntityExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(value = {ResourceNotFoundException.class})
    protected ResponseEntity<Object> handleResourceNotFoundException(ResourceNotFoundException ex, WebRequest request) {
        ResMessage<Object> resMessage = new ResMessage<>(HttpStatus.NOT_FOUND, ex.getMessage());
        return handleExceptionInternal(ex, resMessage, new HttpHeaders(), HttpStatus.NOT_FOUND, request);
    }

    @ExceptionHandler(ServiceException.class)
    public ResponseEntity<Object> handleServiceException(ServiceException e, WebRequest request) {
        ResMessage<Object> resMessage = new ResMessage<>(HttpStatus.BAD_REQUEST, e.getMessage());
        return handleExceptionInternal(e, resMessage, new HttpHeaders(), HttpStatus.BAD_REQUEST, request);
    }
}
