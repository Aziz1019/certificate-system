package com.epam.esm.responseMessage;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ResMessage {
    private int statusCode;
    private String message;
    private Object data;

    public ResMessage(int statusCode, String message) {
        this.statusCode = statusCode;
        this.message = message;
    }

    public ResMessage(int statusCode, String message, Object data) {
        this.statusCode = statusCode;
        this.message = message;
        this.data = data;
    }
}
