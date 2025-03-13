package com.mdotm.pets.service;

import com.mdotm.pets.assembler.PageableAssembler;
import com.mdotm.pets.assembler.PetAssembler;
import com.mdotm.pets.controller.dto.CreatePetInformationRequestDto;
import com.mdotm.pets.controller.dto.UpdatePetInformationRequestDto;
import com.mdotm.pets.domain.Pet;
import com.mdotm.pets.exception.FieldToUpdateIsNotAllowedException;
import com.mdotm.pets.exception.PetInformationNotFoundException;
import com.mdotm.pets.repository.GenericPetRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

@Component
@RequiredArgsConstructor
public class PetServiceImpl implements PetService {

    private final GenericPetRepository petRepository;
    private final PageableAssembler pageableAssembler;
    private final PetAssembler petAssembler;

    @Override
    public Pet createPet(CreatePetInformationRequestDto createPetInformationRequestDto) {
        return petRepository.save(petAssembler.buildPet(createPetInformationRequestDto));
    }

    @Override
    public List<Pet> getAllPets(String name, String species, Integer age, String ownerName, int page, int size, List<String> sort) {
        Pageable pageable = pageableAssembler.buildPageable(page, size, sort);
        Page<Pet> petPage = petRepository.findAll(name, species, age, ownerName, pageable);

        return petPage.getContent();
    }

    @Override
    public Pet getPetById(Long petId) {
        return petRepository.findById(petId).orElseThrow(() -> new PetInformationNotFoundException("Pet with ID = " + petId + " not found"));
    }

    @Override
    public Pet updatePet(Long petId, UpdatePetInformationRequestDto updatePetInformationRequestDto) {
        Pet petToUpdate = petRepository.findById(petId).orElseThrow(() -> new PetInformationNotFoundException("Pet with ID = " + petId + " not found"));
        petToUpdate.setName(updatePetInformationRequestDto.petName());
        petToUpdate.setAge(updatePetInformationRequestDto.age());
        petToUpdate.setOwnerName(updatePetInformationRequestDto.ownerName());
        return petRepository.update(petToUpdate);
    }

    @Override
    public void deletePet(Long petId) {
        petRepository.deleteById(petId);
    }

    @Override
    public Pet patchPet(Long petId, Map<String, Object> updatedProperties) {
        Pet petToPatch = petRepository.findById(petId).orElseThrow(() -> new PetInformationNotFoundException("Pet with ID = " + petId + " not found"));

        updatedProperties.forEach((key, value) -> {
            switch (key) {
                case "name" -> petToPatch.setName((String) value);
                case "species" -> petToPatch.setSpecies((String) value);
                case "age" -> petToPatch.setAge((Integer) value);
                case "ownerName" -> petToPatch.setOwnerName((String) value);
                default -> throw new FieldToUpdateIsNotAllowedException("Invalid field: " + key);
            }
        });
        return petRepository.update(petToPatch);
    }
}
