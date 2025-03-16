package com.mdotm.pets.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.BAD_REQUEST, reason = "Field To Update Is Not Allowed")
public class FieldToUpdateIsNotAllowedException extends RuntimeException {
    public FieldToUpdateIsNotAllowedException(String message) {
        super(message);
    }
}
