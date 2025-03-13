package com.mdotm.pets.jpa.mapper;

import com.mdotm.pets.domain.Pet;
import com.mdotm.pets.jpa.entity.PetEntity;

public interface PetEntityMapper {

    PetEntity toEntity(Pet pet);

    Pet fromEntity(PetEntity petEntity);
}
