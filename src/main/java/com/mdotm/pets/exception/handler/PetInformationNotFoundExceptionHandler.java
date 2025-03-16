package com.mdotm.pets.exception.handler;

import com.mdotm.pets.exception.PetInformationNotFoundException;
import com.mdotm.pets.exception.handler.response.ErrorResponse;
import lombok.extern.log4j.Log4j2;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.LocalDateTime;

@ControllerAdvice
@Order(1)
@Log4j2
public class PetInformationNotFoundExceptionHandler {

    @ExceptionHandler(PetInformationNotFoundException.class)
    public ResponseEntity<ErrorResponse> handlePetInformationNotFound(PetInformationNotFoundException ex) {
        log.error("Error occurred = {}", ex.getMessage());
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(new ErrorResponse("Pet information with such pet ID is not found", LocalDateTime.now()));
    }
}