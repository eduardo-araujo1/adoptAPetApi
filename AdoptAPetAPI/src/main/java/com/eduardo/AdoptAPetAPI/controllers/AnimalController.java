package com.eduardo.AdoptAPetAPI.controllers;

import com.eduardo.AdoptAPetAPI.dto.AnimalDTO;
import com.eduardo.AdoptAPetAPI.services.AnimalService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@RestController
@RequiredArgsConstructor
@RequestMapping("animals")
public class AnimalController {

    private final AnimalService service;

    @PostMapping
    public ResponseEntity<AnimalDTO> registerAnimal(@Valid @RequestBody AnimalDTO dto){
       var animalDto = service.registerAnimal(dto);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
                .buildAndExpand(animalDto.getId()).toUri();
        return ResponseEntity.created(uri).body(animalDto);
    }
}
