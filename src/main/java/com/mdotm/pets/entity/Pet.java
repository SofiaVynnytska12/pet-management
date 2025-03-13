package com.mdotm.pets.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "pets")
@AllArgsConstructor
@NoArgsConstructor
public class Pet {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "pet_id")
    private Long petId;

    @Column(name = "name", nullable = false, length = 50)
    private String name;

    @Column(name = "species", nullable = false)
    private String species;

    @Column(name = "age")
    private Integer age;

    @Column(name = "ownerName", length = 50)
    private String ownerName;

}

