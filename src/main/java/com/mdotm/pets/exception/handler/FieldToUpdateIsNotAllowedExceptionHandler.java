package com.mdotm.pets.exception.handler;

import com.mdotm.pets.exception.FieldToUpdateIsNotAllowedException;
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
public class FieldToUpdateIsNotAllowedExceptionHandler {

    @ExceptionHandler(FieldToUpdateIsNotAllowedException.class)
    public ResponseEntity<ErrorResponse> handleFieldToUpdateIsNotAllowed(FieldToUpdateIsNotAllowedException ex) {
        log.error("Error occurred = {}", ex.getMessage());
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(new ErrorResponse("Field to update is either does not exist or is not allowed", LocalDateTime.now()));
    }
}