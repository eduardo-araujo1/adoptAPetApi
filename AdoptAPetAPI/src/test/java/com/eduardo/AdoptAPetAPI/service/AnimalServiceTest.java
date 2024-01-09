package com.eduardo.AdoptAPetAPI.service;

import com.eduardo.AdoptAPetAPI.converter.AnimalConverter;
import com.eduardo.AdoptAPetAPI.dto.AnimalDTO;
import com.eduardo.AdoptAPetAPI.dto.ShelterDTO;
import com.eduardo.AdoptAPetAPI.entities.Animal;
import com.eduardo.AdoptAPetAPI.entities.Shelter;
import com.eduardo.AdoptAPetAPI.enums.AnimalColor;
import com.eduardo.AdoptAPetAPI.enums.AnimalSize;
import com.eduardo.AdoptAPetAPI.enums.AnimalType;
import com.eduardo.AdoptAPetAPI.repositories.AnimalRepository;
import com.eduardo.AdoptAPetAPI.services.AnimalService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class AnimalServiceTest {

    @Mock
    AnimalRepository animalRepository;

    @Mock
    AnimalConverter animalConverter;

    @InjectMocks
    AnimalService animalService;

    @Test
    public void testRegisterAnimal() {
        AnimalDTO animalDTO = AnimalDTO.builder()
                .id(1L)
                .breed("Labrador")
                .type(AnimalType.DOG)
                .color(AnimalColor.BLACK)
                .size(AnimalSize.LARGE)
                .shelter(ShelterDTO.builder()
                        .name("Shelter test")
                        .address("Rua paraiba")
                        .number("333")
                        .build())
                .build();


        Animal animal = Animal.builder()
                .id(1L)
                .breed("Labrador")
                .type(AnimalType.DOG)
                .color(AnimalColor.BLACK)
                .size(AnimalSize.LARGE)
                .shelter(Shelter.builder()
                        .name("Shelter test")
                        .address("Rua paraiba")
                        .number("333")
                        .build())
                .build();


        when(animalConverter.toEntity(animalDTO)).thenReturn(animal);
        when(animalRepository.save(animal)).thenReturn(animal);
        when(animalConverter.toDTO(animal)).thenReturn(animalDTO);


        AnimalDTO result = animalService.registerAnimal(animalDTO);

        assertEquals(animalDTO, result);

        verify(animalConverter, times(1)).toEntity(animalDTO);
        verify(animalRepository, times(1)).save(animal);
        verify(animalConverter, times(1)).toDTO(animal);
    }
}
