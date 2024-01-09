package com.eduardo.AdoptAPetAPI.services;

import com.eduardo.AdoptAPetAPI.converter.AnimalConverter;
import com.eduardo.AdoptAPetAPI.dto.AnimalDTO;
import com.eduardo.AdoptAPetAPI.entities.Animal;
import com.eduardo.AdoptAPetAPI.entities.User;
import com.eduardo.AdoptAPetAPI.exceptions.UserAlreadyRegisteredException;
import com.eduardo.AdoptAPetAPI.repositories.AnimalRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AnimalService {

    private final AnimalRepository animalRepository;
    private final AnimalConverter animalConverter;

    @Transactional
    public AnimalDTO registerAnimal(AnimalDTO dto){
        Animal animalTosave = animalConverter.toEntity(dto);
        Animal savedAnimal = animalRepository.save(animalTosave);
        return animalConverter.toDTO(savedAnimal);
    }




}
