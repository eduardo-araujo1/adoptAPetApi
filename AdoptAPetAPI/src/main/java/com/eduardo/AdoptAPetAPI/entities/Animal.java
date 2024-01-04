package com.eduardo.AdoptAPetAPI.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "animals")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Animal {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;


    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "shelter_id", referencedColumnName = "id")
    private Shelter shelter;

    @Embedded
    private AnimalCharacteristics characteristics;
}
