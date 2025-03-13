package com.mdotm.pets.jpa.repository.specification;

import com.mdotm.pets.jpa.entity.PetEntity;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;

public class PetSpecification {

    public static Specification<PetEntity> withFilters(String name, String species, Integer age, String ownerName) {
        return (root, query, criteriaBuilder) -> {
            Predicate predicate = criteriaBuilder.conjunction();

            if (name != null) {
                predicate = criteriaBuilder.and(predicate, criteriaBuilder.like(criteriaBuilder.lower(root.get("name")), "%" + name.toLowerCase() + "%"));
            }
            if (species != null) {
                predicate = criteriaBuilder.and(predicate, criteriaBuilder.like(criteriaBuilder.lower(root.get("species")), "%" + species.toLowerCase() + "%"));
            }
            if (age != null) {
                predicate = criteriaBuilder.and(predicate, criteriaBuilder.equal(root.get("age"), age));
            }
            if (ownerName != null) {
                predicate = criteriaBuilder.and(predicate, criteriaBuilder.like(criteriaBuilder.lower(root.get("ownerName")), "%" + ownerName.toLowerCase() + "%"));
            }

            return predicate;
        };
    }
}

