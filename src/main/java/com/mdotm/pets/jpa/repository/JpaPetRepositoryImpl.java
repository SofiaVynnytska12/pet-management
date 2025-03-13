package com.mdotm.pets.jpa.repository;

import com.mdotm.pets.domain.Pet;
import com.mdotm.pets.jpa.entity.PetEntity;
import com.mdotm.pets.jpa.mapper.PetEntityMapper;
import com.mdotm.pets.jpa.repository.specification.PetSpecification;
import com.mdotm.pets.repository.GenericPetRepository;
import jakarta.persistence.EntityManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.support.JpaEntityInformationSupport;
import org.springframework.data.jpa.repository.support.SimpleJpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Repository
@Transactional
public class JpaPetRepositoryImpl implements GenericPetRepository {

    private final PetEntityMapper petEntityMapper;
    private final SimpleJpaRepository<PetEntity, Long> repository;

    @Autowired
    public JpaPetRepositoryImpl(EntityManager entityManager, PetEntityMapper petEntityMapper) {
        this.petEntityMapper = petEntityMapper;
        this.repository = new SimpleJpaRepository<>(JpaEntityInformationSupport.getEntityInformation(PetEntity.class, entityManager), entityManager);
    }

    @Override
    public Pet save(Pet pet) {
        PetEntity petEntity = petEntityMapper.toEntity(pet);
        PetEntity savedEntity = repository.save(petEntity);
        return petEntityMapper.fromEntity(savedEntity);
    }

    @Override
    public Pet update(Pet pet) {
        PetEntity petEntity = repository.getReferenceById(Long.valueOf(pet.getPetId()));
        petEntity.setName(pet.getName());
        petEntity.setAge(pet.getAge());
        petEntity.setOwnerName(pet.getOwnerName());
        PetEntity savedEntity = repository.save(petEntity);
        return petEntityMapper.fromEntity(savedEntity);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<Pet> findAll(String name, String species, Integer age, String ownerName, Pageable pageable) {
        Specification<PetEntity> petSpecification = PetSpecification.withFilters(name, species, age, ownerName);
        Page<PetEntity> petEntities = repository.findAll(petSpecification, pageable);

        List<Pet> pets = petEntities.getContent().stream()
                .map(petEntityMapper::fromEntity)
                .toList();

        return new PageImpl<>(pets, pageable, petEntities.getTotalElements());
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Pet> findById(Long id) {
        return repository.findById(id).map(petEntityMapper::fromEntity);
    }

    @Override
    public void deleteById(Long id) {
        repository.deleteById(id);
    }
}
