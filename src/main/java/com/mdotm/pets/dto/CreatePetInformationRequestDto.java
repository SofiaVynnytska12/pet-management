package com.mdotm.pets.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

@Schema(description = "DTO representing a request for pet information creation")
@JsonInclude(JsonInclude.Include.NON_NULL)
public record CreatePetInformationRequestDto (
        @Schema(description = "Name of the pet.", example = "Buddy")
        @NotBlank(message = "Pet name is required")
        @Size(max = 50, message = "Pet name cannot exceed 50 characters")
        String petName,

        @Schema(description = "Species of the pet. Allowed values: DOG, CAT, RABBIT, FISH, SNAKE", example = "DOG")
        @NotNull(message = "Pet species is required")
        String species,

        @Schema(description = "Age of the pet. Must be 0 or greater.", example = "3")
        @Min(value = 0, message = "Age must be greater than or equal to 0")
        Integer age,

        @Schema(description = "Name of the pet's owner.", example = "John Doe")
        @Size(max = 50, message = "Owner name cannot exceed 50 characters")
        String ownerName
) {}
