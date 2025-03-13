package com.mdotm.pets.exception.handler.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Schema(description = "Object representing the error response which can be received from API call")
public class ErrorResponse {

    @Schema(description = "Description of the error which occurred", example = "Pet name is required")
    private String message;
    @Schema(description = "Time when this error has occurred", example = "2025-03-25T16:37:58.689Z")
    private LocalDateTime timestamp;
}
