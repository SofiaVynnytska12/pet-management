package com.mdotm.pets.controller.mapper;

import com.mdotm.pets.controller.dto.PetDto;
import com.mdotm.pets.domain.Pet;

public interface PetDtoMapper {

    PetDto toDto(Pet pet);
    Pet fromDto(PetDto petDto);
}
