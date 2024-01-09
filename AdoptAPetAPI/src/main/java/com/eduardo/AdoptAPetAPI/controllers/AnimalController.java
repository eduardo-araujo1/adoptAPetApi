package com.eduardo.AdoptAPetAPI.controllers;

import com.eduardo.AdoptAPetAPI.dto.AnimalDTO;
import com.eduardo.AdoptAPetAPI.services.AnimalService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@RestController
@RequiredArgsConstructor
@RequestMapping("animals")
public class AnimalController {

    private final AnimalService service;

    @PostMapping
    public ResponseEntity<AnimalDTO> registerAnimalForAdoption(@Valid @RequestBody AnimalDTO dto){
       var animalDto = service.registerAnimalForAdoption(dto);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
                .buildAndExpand(animalDto.getId()).toUri();
        return ResponseEntity.created(uri).body(animalDto);
    }

    @GetMapping("/all")
    public ResponseEntity<Page<AnimalDTO>> getAll(@RequestParam (defaultValue = "0") int page, @RequestParam(defaultValue = "10") int size){
        Page<AnimalDTO> returnAll = service.findAll(page, size);
        return ResponseEntity.ok().body(returnAll);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> adoptAnimal(@PathVariable Long id){
        service.adoptAnimal(id);
        return ResponseEntity.noContent().build();
    }
}
