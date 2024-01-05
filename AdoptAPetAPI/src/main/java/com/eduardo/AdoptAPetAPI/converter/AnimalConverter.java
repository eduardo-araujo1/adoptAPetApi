package com.eduardo.AdoptAPetAPI.converter;

import com.eduardo.AdoptAPetAPI.dto.AnimalDTO;
import com.eduardo.AdoptAPetAPI.entities.Animal;
import org.springframework.stereotype.Component;

@Component
public class AnimalConverter {

    public Animal toEntity(AnimalDTO animalDTO) {
        return Animal.builder()
                .id(animalDTO.getId())
                .name(animalDTO.getName())
                .shelter(ShelterConverter.toEntity(animalDTO.getShelter()))
                .type(animalDTO.getType())
                .color(animalDTO.getColor())
                .size(animalDTO.getSize())
                .build();
    }

    public AnimalDTO toDTO(Animal animal) {
        return AnimalDTO.builder()
                .id(animal.getId())
                .name(animal.getName())
                .shelter(ShelterConverter.toDTO(animal.getShelter()))
                .type(animal.getType())
                .color(animal.getColor())
                .size(animal.getSize())
                .build();
    }

}
