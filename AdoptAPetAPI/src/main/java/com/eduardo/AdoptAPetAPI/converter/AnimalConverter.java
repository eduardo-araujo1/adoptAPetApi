package com.eduardo.AdoptAPetAPI.converter;

import com.eduardo.AdoptAPetAPI.dto.AnimalDTO;
import com.eduardo.AdoptAPetAPI.dto.ShelterDTO;
import com.eduardo.AdoptAPetAPI.entities.Animal;
import com.eduardo.AdoptAPetAPI.entities.Shelter;
import org.springframework.stereotype.Component;

@Component
public class AnimalConverter {

    public Animal toEntity(AnimalDTO animalDTO) {
        return Animal.builder()
                .id(animalDTO.getId())
                .breed(animalDTO.getBreed())
                .type(animalDTO.getType())
                .color(animalDTO.getColor())
                .size(animalDTO.getSize())
                .shelter(toShelterEntity(animalDTO.getShelter()))
                .build();
    }

    public AnimalDTO toDTO(Animal animal) {
        return AnimalDTO.builder()
                .id(animal.getId())
                .breed(animal.getBreed())
                .type(animal.getType())
                .color(animal.getColor())
                .size(animal.getSize())
                .shelter(toShelterDTO(animal.getShelter()))
                .build();
    }

    public Shelter toShelterEntity(ShelterDTO shelterDTO) {
        return Shelter.builder()
                .name(shelterDTO.getName())
                .address(shelterDTO.getAddress())
                .number(shelterDTO.getNumber())
                .build();
    }

    public ShelterDTO toShelterDTO(Shelter shelter) {
        return ShelterDTO.builder()
                .name(shelter.getName())
                .address(shelter.getAddress())
                .number(shelter.getNumber())
                .build();
    }

}
