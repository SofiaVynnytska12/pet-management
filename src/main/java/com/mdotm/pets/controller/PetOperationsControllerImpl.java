package com.mdotm.pets.controller;

import com.mdotm.pets.controller.dto.CreatePetInformationRequestDto;
import com.mdotm.pets.controller.dto.PetDto;
import com.mdotm.pets.controller.dto.UpdatePetInformationRequestDto;
import com.mdotm.pets.controller.mapper.PetDtoMapper;
import com.mdotm.pets.domain.Pet;
import com.mdotm.pets.service.PetService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

@Component
@RequiredArgsConstructor
public class PetOperationsControllerImpl implements PetOperationsController {

    private final PetDtoMapper petDtoMapper;
    private final PetService petService;

    @Override
    public ResponseEntity<PetDto> createPet(CreatePetInformationRequestDto createPetInformationRequestDto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(petDtoMapper.toDto(petService.createPet(createPetInformationRequestDto)));
    }

    @Override
    public ResponseEntity<PetDto> getPetById(Long id) {
        return ResponseEntity.status(HttpStatus.OK).body(petDtoMapper.toDto(petService.getPetById(id)));
    }

    @Override
    public ResponseEntity<List<PetDto>> getAllPets(int page, int size, List<String> sort, String name, String species, Integer age, String ownerName) {
        List<Pet> pets = petService.getAllPets(name, species, age, ownerName, page, size, sort);
        return ResponseEntity.status(HttpStatus.OK).body(pets.stream()
                .map(petDtoMapper::toDto)
                .toList());
    }

    @Override
    public ResponseEntity<PetDto> updatePetById(Long id, UpdatePetInformationRequestDto updatePetInformationRequestDto) {
        return ResponseEntity.status(HttpStatus.OK).body(petDtoMapper.toDto(petService.updatePet(id, updatePetInformationRequestDto)));
    }

    @Override
    public ResponseEntity<Void> deletePetById(Long id) {
        petService.deletePet(id);
        return ResponseEntity.noContent().build();
    }

    @Override
    public ResponseEntity<PetDto> patchPetById(Long id, Map<String, Object> updatedProperties) {
        return ResponseEntity.status(HttpStatus.OK).body(petDtoMapper.toDto(petService.patchPet(id, updatedProperties)));
    }
}
