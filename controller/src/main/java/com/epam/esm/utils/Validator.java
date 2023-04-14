package com.epam.esm.utils;

import com.epam.esm.exception.ServiceException;
import org.springframework.validation.BindingResult;

import java.util.ArrayList;
import java.util.List;


public class Validator {

    public static void validate(Long id, BindingResult result) throws ServiceException {
        if(id == null) id = 0L;
        if (result.hasErrors()) {
            List<String> violations = new ArrayList<>();
            result.getFieldErrors().forEach(fieldError
                    -> violations.add(fieldError.getDefaultMessage()));
            throw new ServiceException(violations.toArray(), "Resource can not be modified for id: " + id);
        }
    }
}
