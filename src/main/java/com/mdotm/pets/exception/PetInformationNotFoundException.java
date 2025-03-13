package com.mdotm.pets.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.NOT_FOUND, reason = "Pilotes Order Not Found")
public class PetInformationNotFoundException extends PetManagementBusinessException {
    public PetInformationNotFoundException(String message) {
        super(message);
    }
}
