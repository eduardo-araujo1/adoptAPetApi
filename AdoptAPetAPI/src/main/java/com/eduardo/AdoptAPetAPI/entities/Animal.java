package com.eduardo.AdoptAPetAPI.entities;

import com.eduardo.AdoptAPetAPI.enums.AnimalColor;
import com.eduardo.AdoptAPetAPI.enums.AnimalSize;
import com.eduardo.AdoptAPetAPI.enums.AnimalType;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "animals")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Animal {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "breed")
    private String breed;


    @Enumerated(EnumType.STRING)
    private AnimalColor color;

    @Enumerated(EnumType.STRING)
    private AnimalSize size;

    @Enumerated(EnumType.STRING)
    private AnimalType type;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "shelter_id", referencedColumnName = "id")
    private Shelter shelter;
}
