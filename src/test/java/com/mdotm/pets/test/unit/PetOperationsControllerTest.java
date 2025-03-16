package com.mdotm.pets.test.unit;

import com.mdotm.pets.controller.PetOperationsControllerImpl;
import com.mdotm.pets.controller.dto.CreatePetInformationRequestDto;
import com.mdotm.pets.controller.dto.PetDto;
import com.mdotm.pets.controller.dto.UpdatePetInformationRequestDto;
import com.mdotm.pets.controller.mapper.PetDtoMapper;
import com.mdotm.pets.domain.Pet;
import com.mdotm.pets.exception.PetInformationNotFoundException;
import com.mdotm.pets.service.PetService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class PetOperationsControllerTest {

    @Mock
    private PetDtoMapper petDtoMapper;

    @Mock
    private PetService petService;

    @InjectMocks
    private PetOperationsControllerImpl petOperationsController;

    private Pet pet;
    private PetDto petDto;

    @BeforeEach
    void setUp() {
        pet = new Pet(String.valueOf(1L), "Buddy", "Dog", 5, "Sofiia");
        petDto = new PetDto(pet.getPetId(), pet.getName(), pet.getSpecies(), pet.getAge(), pet.getOwnerName());
    }

    @Test
    void testCreatePet() {
        CreatePetInformationRequestDto requestDto = new CreatePetInformationRequestDto("Buddy", "Dog", 6, "Sofiia");

        when(petService.createPet(requestDto)).thenReturn(pet);
        when(petDtoMapper.toDto(pet)).thenReturn(petDto);

        ResponseEntity<PetDto> response = petOperationsController.createPet(requestDto);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(petDto, response.getBody());
    }

    @Test
    void testGetPetById() {
        when(petService.getPetById(1L)).thenReturn(pet);
        when(petDtoMapper.toDto(pet)).thenReturn(petDto);

        ResponseEntity<PetDto> response = petOperationsController.getPetById(1L);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(petDto, response.getBody());
    }

    @Test
    void testGetPetByIdNotFound() {
        when(petService.getPetById(1L)).thenThrow(new PetInformationNotFoundException("Pet not found"));

        Exception exception = assertThrows(PetInformationNotFoundException.class, () -> petOperationsController.getPetById(1L));
        assertEquals("Pet not found", exception.getMessage());
    }

    @Test
    void testGetAllPets() {
        List<Pet> pets = List.of(pet);
        when(petService.getAllPets(null, null, null, null, 0, 10, null)).thenReturn(pets);
        when(petDtoMapper.toDto(pet)).thenReturn(petDto);

        ResponseEntity<List<PetDto>> response = petOperationsController.getAllPets(0, 10, null, null, null, null, null);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(1, response.getBody().size());
        assertEquals(petDto, response.getBody().get(0));
    }

    @Test
    void testUpdatePetById() {
        UpdatePetInformationRequestDto updateDto = new UpdatePetInformationRequestDto("Buddy", 11,"Sam");

        when(petService.updatePet(1L, updateDto)).thenReturn(pet);
        when(petDtoMapper.toDto(pet)).thenReturn(petDto);

        ResponseEntity<PetDto> response = petOperationsController.updatePetById(1L, updateDto);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(petDto, response.getBody());
    }

    @Test
    void testUpdatePetByIdNotFound() {
        UpdatePetInformationRequestDto updateDto = new UpdatePetInformationRequestDto("Buddy", 9,"Clara");

        when(petService.updatePet(1L, updateDto)).thenThrow(new PetInformationNotFoundException("Pet not found"));

        Exception exception = assertThrows(PetInformationNotFoundException.class, () -> petOperationsController.updatePetById(1L, updateDto));
        assertEquals("Pet not found", exception.getMessage());
    }

    @Test
    void testDeletePetById() {
        doNothing().when(petService).deletePet(1L);

        ResponseEntity<Void> response = petOperationsController.deletePetById(1L);

        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
        verify(petService, times(1)).deletePet(1L);
    }

    @Test
    void testPatchPetById() {
        Map<String, Object> updatedProperties = Map.of("name", "New Name");

        when(petService.patchPet(1L, updatedProperties)).thenReturn(pet);
        when(petDtoMapper.toDto(pet)).thenReturn(petDto);

        ResponseEntity<PetDto> response = petOperationsController.patchPetById(1L, updatedProperties);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(petDto, response.getBody());
    }

    @Test
    void testPatchPetByIdNotFound() {
        Map<String, Object> updatedProperties = Map.of("name", "New Name");

        when(petService.patchPet(1L, updatedProperties)).thenThrow(new PetInformationNotFoundException("Pet not found"));

        Exception exception = assertThrows(PetInformationNotFoundException.class, () -> petOperationsController.patchPetById(1L, updatedProperties));
        assertEquals("Pet not found", exception.getMessage());
    }

}
