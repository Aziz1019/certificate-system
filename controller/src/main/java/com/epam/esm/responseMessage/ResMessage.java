package com.epam.esm.responseMessage;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ResMessage {
    private int statusCode;
    private String message;
    private Object data;
}