package com.mdotm.pets.jpa.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "pets")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class PetEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "pet_id")
    private Long petId;

    @Column(name = "name", nullable = false, length = 50)
    private String name;

    @Column(name = "species", nullable = false, length = 50)
    private String species;

    @Column(name = "age")
    private Integer age;

    @Column(name = "owner_name", length = 50)
    private String ownerName;

    //TODO: create_timestamp and update_timestamp can be added (not needed in domain's pet but useful info to have in DB)

}

