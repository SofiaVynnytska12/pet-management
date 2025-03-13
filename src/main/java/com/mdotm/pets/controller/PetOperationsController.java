package com.mdotm.pets.controller;

import com.mdotm.pets.dto.CreatePetInformationRequestDto;
import com.mdotm.pets.dto.PetDto;
import com.mdotm.pets.dto.UpdatePetInformationRequestDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.web.ErrorResponse;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@Tag(name = "Pets", description = "API for managing pets")
@RestController
@RequestMapping("/pets")
public interface PetOperationsController {

    @Operation(
            summary = "Create a new pet",
            description = "Adds a new pet information to the system.",
            responses = {
                    @ApiResponse(responseCode = "201", description = "Pet successfully created",
                            content = @Content(schema = @Schema(implementation = PetDto.class))),
                    @ApiResponse(responseCode = "400", description = "Invalid request data; operation is not allowed by business",
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = ErrorResponse.class))),
                    @ApiResponse(responseCode = "500", description = "Generic server error occurred; operation can be retried later",
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = ErrorResponse.class)))
            }
    )
    @PostMapping
    PetDto createPet(@RequestBody @Valid CreatePetInformationRequestDto createPetInformationRequestDto);

    @Operation(
            summary = "Get a pet by ID",
            description = "Retrieves pet information by its unique identifier.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Pet details retrieved successfully",
                            content = @Content(schema = @Schema(implementation = PetDto.class))),
                    @ApiResponse(responseCode = "404", description = "Pet not found",
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = ErrorResponse.class))),
                    @ApiResponse(responseCode = "500", description = "Generic server error occurred; operation can be retried later",
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = ErrorResponse.class)))
            }
    )
    @GetMapping("/{id}")
    PetDto getPetById(@PathVariable Long id);

    @Operation(
            summary = "Get all pets",
            description = "Retrieves a paginated list of pets with optional filtering and sorting.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Successfully retrieved pets",
                            content = @Content(schema = @Schema(implementation = PetDto.class))),
                    @ApiResponse(responseCode = "500", description = "Generic server error occurred; operation can be retried later",
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = ErrorResponse.class)))
            }
    )
    @GetMapping
    List<PetDto> getAllPets(@Parameter(description = "Page number (zero-based index)", example = "0")
                            @RequestParam(defaultValue = "0") int page,
                            @Parameter(description = "Number of records per page", example = "10")
                            @RequestParam(defaultValue = "10") int size,
                            @Parameter(description = "Sorting criteria in the format `+field` or `-field`", example = "-age,+name")
                            @RequestParam(required = false) List<String> sort,
                            @Parameter(description = "Filter by pet name (incl. partial filtering)")
                            @RequestParam(required = false) String name,
                            @Parameter(description = "Filter by pet species (incl. partial filtering, case agnostic)", example = "cat")
                            @RequestParam(required = false) String species,
                            @Parameter(description = "Filter by pet age", example = "3")
                            @RequestParam(required = false) Integer age,
                            @Parameter(description = "Filter by owner's name (incl. partial filtering)")
                            @RequestParam(required = false) String ownerName);

    @Operation(
            summary = "Update a pet by ID",
            description = "Replaces all fields of an existing pet.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Pet updated successfully",
                            content = @Content(schema = @Schema(implementation = PetDto.class))),
                    @ApiResponse(responseCode = "400", description = "Invalid input data",
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = ErrorResponse.class))),
                    @ApiResponse(responseCode = "404", description = "Pet not found",
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = ErrorResponse.class))),
                    @ApiResponse(responseCode = "500", description = "Generic server error occurred; operation can be retried later",
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = ErrorResponse.class)))
            }
    )
    @PutMapping("/{id}")
    PetDto updatePetById(@PathVariable Long id, @RequestBody @Valid UpdatePetInformationRequestDto updatePetInformationRequestDto);

    @Operation(
            summary = "Delete a pet by ID",
            description = "Removes a pet from the system.",
            responses = {
                    @ApiResponse(responseCode = "204", description = "Pet deleted successfully"),
                    @ApiResponse(responseCode = "404", description = "Pet not found",
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = ErrorResponse.class))),
                    @ApiResponse(responseCode = "500", description = "Generic server error occurred; operation can be retried later",
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = ErrorResponse.class)))
            }
    )
    @DeleteMapping("/{id}")
    void deletePetById(@PathVariable Long id);

    @Operation(
            summary = "Partially update a pet by ID",
            description = "Allows updating one or more fields of an existing pet.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Pet updated successfully",
                            content = @Content(schema = @Schema(implementation = PetDto.class))),
                    @ApiResponse(responseCode = "400", description = "Invalid update request",
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = ErrorResponse.class))),
                    @ApiResponse(responseCode = "404", description = "Pet not found"),
                    @ApiResponse(responseCode = "500", description = "Generic server error occurred; operation can be retried later",
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = ErrorResponse.class)))
            }
    )
    @PatchMapping("/{id}")
    PetDto patchPetById(@PathVariable Long id, @RequestBody Map<String, Object> updatedProperties);
}
