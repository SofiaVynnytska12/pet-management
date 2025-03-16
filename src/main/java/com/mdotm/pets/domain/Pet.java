package com.mdotm.pets.domain;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
@ToString
public class Pet {

    private String petId;
    private String name;
    private String species;
    private Integer age;
    private String ownerName;
}
