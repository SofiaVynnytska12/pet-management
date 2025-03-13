package com.mdotm.pets.service;

import com.mdotm.pets.controller.dto.CreatePetInformationRequestDto;
import com.mdotm.pets.controller.dto.UpdatePetInformationRequestDto;
import com.mdotm.pets.domain.Pet;

import java.util.List;
import java.util.Map;

public interface PetService {

    Pet createPet(CreatePetInformationRequestDto createPetInformationRequestDto);

    List<Pet> getAllPets(String name, String species, Integer age, String ownerName,
                         int page, int size, List<String> sort);

    Pet getPetById(Long petId);

    Pet updatePet(Long petId, UpdatePetInformationRequestDto updatePetInformationRequestDto);

    void deletePet(Long petId);

    Pet patchPet(Long petId, Map<String, Object> updatedProperties);
}
