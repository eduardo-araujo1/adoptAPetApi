package com.eduardo.AdoptAPetAPI.entities;

import com.eduardo.AdoptAPetAPI.enums.AnimalColor;
import com.eduardo.AdoptAPetAPI.enums.AnimalSize;
import com.eduardo.AdoptAPetAPI.enums.AnimalType;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "users")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "email")
    private String email;

    @Column(name = "isLookingForAnimal")
    private boolean isLookingForAnimal;

    @Enumerated(EnumType.STRING)
    private AnimalColor color;

    @Enumerated(EnumType.STRING)
    private AnimalSize size;

    @Enumerated(EnumType.STRING)
    private AnimalType type;


}
