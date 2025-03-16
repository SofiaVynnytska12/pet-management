package com.mdotm.pets.test.unit;

import com.mdotm.pets.assembler.PageableAssembler;
import com.mdotm.pets.assembler.PetAssembler;
import com.mdotm.pets.controller.dto.CreatePetInformationRequestDto;
import com.mdotm.pets.controller.dto.UpdatePetInformationRequestDto;
import com.mdotm.pets.domain.Pet;
import com.mdotm.pets.exception.FieldToUpdateIsNotAllowedException;
import com.mdotm.pets.exception.PetInformationNotFoundException;
import com.mdotm.pets.repository.GenericPetRepository;
import com.mdotm.pets.service.PetServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class PetServiceTest {

    @Mock
    private GenericPetRepository petRepository;

    @Mock
    private PageableAssembler pageableAssembler;

    @Mock
    private PetAssembler petAssembler;

    @InjectMocks
    private PetServiceImpl petService;

    @Test
    @Transactional
    public void testCreatePet() {
        CreatePetInformationRequestDto dto = new CreatePetInformationRequestDto("Buddy", "Dog", 5, "John");
        Pet pet = new Pet(String.valueOf(1L), "Buddy", "Dog", 5, "John");
        when(petAssembler.buildPet(dto)).thenReturn(pet);
        when(petRepository.save(pet)).thenReturn(pet);

        Pet createdPet = petService.createPet(dto);
        assertNotNull(createdPet);
        verify(petRepository).save(pet);
    }

    @Test
    public void testGetAllPets() {
        Pageable pageable = PageRequest.of(0, 10);
        List<Pet> pets = List.of(
                new Pet(String.valueOf(1L), "Buddy", "Dog", 5, "John"),
                new Pet(String.valueOf(2L), "Max", "Cat", 3, "Alice")
        );
        Page<Pet> petPage = new PageImpl<>(pets, pageable, pets.size());

        when(pageableAssembler.buildPageable(anyInt(), anyInt(), anyList())).thenReturn(pageable);
        when(petRepository.findAll(anyString(), anyString(), any(), anyString(), eq(pageable))).thenReturn(petPage);

        List<Pet> result = petService.getAllPets("Buddy", "Dog", 5, "John", 0, 10, Collections.emptyList());
        assertEquals(result, pets);

        petPage = new PageImpl<>(List.of(new Pet(String.valueOf(3L), "Charlie", "Bird", 2, "Emma")), pageable, 1);
        when(petRepository.findAll(null, null, null, null, pageable)).thenReturn(petPage);

        result = petService.getAllPets(null, null, null, null, 0, 10, Collections.emptyList());
        assertFalse(result.isEmpty());
        assertEquals(1, result.size());
        assertEquals("Charlie", result.get(0).getName());
    }

    @Test
    public void testGetPetByIdFound() {
        Long petId = 1L;
        Pet pet = new Pet(String.valueOf(petId), "Buddy", "Dog", 5, "John");
        when(petRepository.findById(petId)).thenReturn(Optional.of(pet));

        Pet result = petService.getPetById(petId);
        assertNotNull(result);
        assertEquals(pet, result);
    }

    @Test
    public void testGetPetByIdNotFound() {
        when(petRepository.findById(1L)).thenReturn(Optional.empty());
        assertThrows(PetInformationNotFoundException.class, () -> petService.getPetById(1L));
    }

    @Test
    @Transactional
    public void testUpdatePet() {
        Long petId = 1L;
        UpdatePetInformationRequestDto dto = new UpdatePetInformationRequestDto("Max", 4, "Alice");
        Pet pet = new Pet(String.valueOf(petId), "Buddy", "Dog", 5, "John");

        when(petRepository.findById(petId)).thenReturn(Optional.of(pet));
        when(petRepository.update(any(Pet.class))).thenReturn(pet);

        Pet updatedPet = petService.updatePet(petId, dto);
        assertNotNull(updatedPet);
        assertEquals("Max", updatedPet.getName());
        assertEquals(4, updatedPet.getAge());
        assertEquals("Alice", updatedPet.getOwnerName());
    }

    @Test
    public void testUpdatePetNotFound() {
        when(petRepository.findById(1L)).thenReturn(Optional.empty());
        assertThrows(PetInformationNotFoundException.class, () -> petService.updatePet(1L, new UpdatePetInformationRequestDto("Max", 4, "Alice")));
    }

    @Test
    @Transactional
    public void testDeletePet() {
        petService.deletePet(1L);
        verify(petRepository).deleteById(1L);
    }

    @Test
    @Transactional
    public void testPatchPet() {
        Long petId = 1L;
        Pet pet = new Pet(String.valueOf(petId), "Buddy", "Dog", 5, "John");
        Map<String, Object> updates = Map.of("name", "Charlie", "age", 3);

        when(petRepository.findById(petId)).thenReturn(Optional.of(pet));
        when(petRepository.update(any(Pet.class))).thenReturn(pet);

        Pet patchedPet = petService.patchPet(petId, updates);
        assertNotNull(patchedPet);
        assertEquals("Charlie", patchedPet.getName());
        assertEquals(3, patchedPet.getAge());
    }

    @Test
    public void testPatchPetInvalidField() {
        Long petId = 1L;
        Pet pet = new Pet(String.valueOf(petId), "Buddy", "Dog", 5, "John");
        Map<String, Object> updates = Map.of("invalidField", "value");

        when(petRepository.findById(petId)).thenReturn(Optional.of(pet));
        assertThrows(FieldToUpdateIsNotAllowedException.class, () -> petService.patchPet(petId, updates));
    }

    @Test
    public void testPatchPetNotFound() {
        when(petRepository.findById(1L)).thenReturn(Optional.empty());
        assertThrows(PetInformationNotFoundException.class, () -> petService.patchPet(1L, Map.of("name", "Charlie")));
    }

}
