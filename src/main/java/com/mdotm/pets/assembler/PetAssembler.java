package com.mdotm.pets.assembler;

import com.mdotm.pets.controller.dto.CreatePetInformationRequestDto;
import com.mdotm.pets.domain.Pet;
import org.springframework.stereotype.Component;

@Component
public class PetAssembler {

    public Pet buildPet(CreatePetInformationRequestDto createPetInformationRequestDto) {
        return new Pet(
                null,
                createPetInformationRequestDto.petName(),
                createPetInformationRequestDto.species(),
                createPetInformationRequestDto.age(),
                createPetInformationRequestDto.ownerName()
        );
    }
}
