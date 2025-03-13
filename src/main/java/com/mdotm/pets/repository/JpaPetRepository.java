package com.mdotm.pets.repository;

import com.mdotm.pets.entity.Pet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface JpaPetRepository extends JpaRepository<Pet, Long>, JpaSpecificationExecutor<Pet> {

}
