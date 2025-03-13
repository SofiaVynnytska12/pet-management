package com.mdotm.pets.repository;

import com.mdotm.pets.domain.Pet;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.NoRepositoryBean;

import java.util.Optional;

@NoRepositoryBean
public interface GenericPetRepository {

    Pet save(Pet pet);

    Pet update(Pet pet);

    Page<Pet> findAll(String name, String species, Integer age, String ownerName, Pageable pageable);

    Optional<Pet> findById(Long id);

    void deleteById(Long id);
}
