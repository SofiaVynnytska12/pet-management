package com.mdotm.pets.jpa.mapper;

import com.mdotm.pets.domain.Pet;
import com.mdotm.pets.jpa.entity.PetEntity;
import org.springframework.stereotype.Component;

@Component
public class PetEntityMapperImpl implements PetEntityMapper {

    @Override
    public PetEntity toEntity(Pet pet) {
        return new PetEntity(null, pet.getName(), pet.getSpecies(), pet.getAge(), pet.getOwnerName());
    }

    @Override
    public Pet fromEntity(PetEntity petEntity) {
        return Pet.builder()
                .petId(String.valueOf(petEntity.getPetId()))
                .name(petEntity.getName())
                .species(petEntity.getSpecies())
                .age(petEntity.getAge())
                .ownerName(petEntity.getOwnerName())
                .build();
    }
}
