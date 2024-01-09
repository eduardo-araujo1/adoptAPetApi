package com.eduardo.AdoptAPetAPI.services;

import com.eduardo.AdoptAPetAPI.converter.AnimalConverter;
import com.eduardo.AdoptAPetAPI.dto.AnimalDTO;
import com.eduardo.AdoptAPetAPI.entities.Animal;
import com.eduardo.AdoptAPetAPI.entities.User;
import com.eduardo.AdoptAPetAPI.exceptions.UserAlreadyRegisteredException;
import com.eduardo.AdoptAPetAPI.repositories.AnimalRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AnimalService {

    private final AnimalRepository animalRepository;
    private final AnimalConverter animalConverter;

    @Transactional
    public AnimalDTO registerAnimalForAdoption(AnimalDTO dto){
        Animal animalTosave = animalConverter.toEntity(dto);
        Animal savedAnimal = animalRepository.save(animalTosave);
        return animalConverter.toDTO(savedAnimal);
    }

    @Transactional(readOnly = true)
    public Page<AnimalDTO> findAll(int page, int size){
        PageRequest pageRequest = PageRequest.of(page, size);
        Page<Animal> animalsPage = animalRepository.findAll(pageRequest);
        return animalsPage.map(animalConverter::toDTO);
    }




}
