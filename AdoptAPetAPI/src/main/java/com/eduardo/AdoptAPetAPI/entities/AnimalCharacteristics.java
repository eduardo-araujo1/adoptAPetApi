package com.eduardo.AdoptAPetAPI.entities;

import com.eduardo.AdoptAPetAPI.enums.AnimalColor;
import com.eduardo.AdoptAPetAPI.enums.AnimalSize;
import com.eduardo.AdoptAPetAPI.enums.AnimalType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Embeddable
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AnimalCharacteristics {


    @Enumerated(EnumType.STRING)
    private AnimalColor color;

    @Enumerated(EnumType.STRING)
    private AnimalSize size;

    @Enumerated(EnumType.STRING)
    private AnimalType type;
}
