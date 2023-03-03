package com.epam.esm.responseMessage;

import lombok.Data;
import org.springframework.http.HttpStatus;

/**
 * custom class used as return type of controller methods
 */

@Data
public class ResponseContentMessageStatus<T> {
    private HttpStatus status;
    private String message;
    private T content;

    /**
     * used for handling InvalidRequestBodyException
     *
     * @param content T response body
     * @param status HttpStatus response status
     * @param message String response message
     */
    public ResponseContentMessageStatus(T content, HttpStatus status, String message) {
        this.content = content;
        this.status = status;
        this.message = message;
    }

    /**
     * used for responses with no content
     *
     * @param status HttpStatus response status
     * @param message String response message
     */
    public ResponseContentMessageStatus(HttpStatus status, String message) {
        this.content = null;
        this.status = status;
        this.message = message;
    }

    /**
     * used for responses with no message
     *
     * @param content T response body
     */
    public ResponseContentMessageStatus(T content) {
        this.content = content;
    }
}