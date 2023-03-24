package com.epam.esm.exception;

import com.epam.esm.responseMessage.ResMessage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

/**

 The {@code ControllerExceptionHandler} class is a global exception handler that catches exceptions
 thrown from the controller methods annotated with the {@code @RestController} annotation.

 This class contains methods annotated with the {@code @ExceptionHandler} annotation that handle
 the different types of exceptions thrown from the controller methods.
 */
@Slf4j
@ControllerAdvice
public class ControllerExceptionHandler {

    public ControllerExceptionHandler() {
    }

    @ResponseBody
    @ExceptionHandler(ResourceNotFoundException.class)
    public ResMessage<Object> handleResourceNotFoundException(ResourceNotFoundException e) {
        return new ResMessage<>(HttpStatus.BAD_REQUEST, e.getMessage());
    }

    @ResponseBody
    @ExceptionHandler(ServiceException.class)
    public ResMessage<Object> handleServiceException(ServiceException e) {
        return new ResMessage<>(HttpStatus.BAD_REQUEST, e.getMessage());
    }

}
