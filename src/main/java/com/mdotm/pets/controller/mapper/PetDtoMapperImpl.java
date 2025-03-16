package com.mdotm.pets.controller.mapper;

import com.mdotm.pets.controller.dto.PetDto;
import com.mdotm.pets.domain.Pet;
import org.springframework.stereotype.Component;

@Component
public class PetDtoMapperImpl implements PetDtoMapper {

    @Override
    public PetDto toDto(Pet pet) {
        return new PetDto(pet.getPetId(), pet.getName(), pet.getSpecies(), pet.getAge(), pet.getOwnerName());
    }

    @Override
    public Pet fromDto(PetDto petDto) {
        return Pet.builder()
                .petId(petDto.id())
                .name(petDto.name())
                .species(petDto.species())
                .age(petDto.age())
                .ownerName(petDto.ownerName())
                .build();
    }
}
