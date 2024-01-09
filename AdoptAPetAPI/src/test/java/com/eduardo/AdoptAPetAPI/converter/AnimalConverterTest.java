package com.eduardo.AdoptAPetAPI.converter;

import com.eduardo.AdoptAPetAPI.dto.AnimalDTO;
import com.eduardo.AdoptAPetAPI.dto.ShelterDTO;
import com.eduardo.AdoptAPetAPI.entities.Animal;
import com.eduardo.AdoptAPetAPI.entities.Shelter;
import com.eduardo.AdoptAPetAPI.enums.AnimalColor;
import com.eduardo.AdoptAPetAPI.enums.AnimalSize;
import com.eduardo.AdoptAPetAPI.enums.AnimalType;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class AnimalConverterTest {

    @InjectMocks
    private AnimalConverter animalConverter;

    @Test
    public void testToEntity(){
        AnimalDTO animalDTO = AnimalDTO.builder()
                .id(1L)
                .breed("Pitbull")
                .type(AnimalType.DOG)
                .color(AnimalColor.BLACK)
                .size(AnimalSize.LARGE)
                .shelter(ShelterDTO.builder()
                        .name("Shelter test")
                        .address("Rua paraiba")
                        .number("333")
                        .build())
                .build();

        Animal result = animalConverter.toEntity(animalDTO);

        assertEquals(animalDTO.getId(), result.getId());
        assertEquals(animalDTO.getBreed(), result.getBreed());
    }

    @Test
    public void testToDTO(){
        Animal animal = Animal.builder()
                .id(1L)
                .breed("Pitbull")
                .type(AnimalType.DOG)
                .color(AnimalColor.BLACK)
                .size(AnimalSize.LARGE)
                .shelter(Shelter.builder()
                        .name("Shelter test")
                        .address("Rua paraiba")
                        .number("333")
                        .build())
                .build();

        AnimalDTO result = animalConverter.toDTO(animal);

        assertEquals(animal.getBreed(), result.getBreed());
        assertEquals(animal.getId(), result.getId());
    }

    @Test
    public void testShelterToEntity(){
        ShelterDTO shelterDTO = ShelterDTO.builder()
                .name("Shelter test")
                .address("Rua paraiba")
                .number("333")
                .build();

        Shelter result = animalConverter.toShelterEntity(shelterDTO);

        assertEquals(shelterDTO.getName(), result.getName());
        assertEquals(shelterDTO.getAddress(), result.getAddress());
        assertEquals(shelterDTO.getNumber(), result.getNumber());
    }

    @Test
    public void testShelterToDTO(){
        Shelter shelter = Shelter.builder()
                .name("Shelter test")
                .address("Rua paraiba")
                .number("333")
                .build();

        ShelterDTO result = animalConverter.toShelterDTO(shelter);

        assertEquals(shelter.getName(), result.getName());
        assertEquals(shelter.getAddress(), result.getAddress());
        assertEquals(shelter.getNumber(), result.getNumber());
    }
}
