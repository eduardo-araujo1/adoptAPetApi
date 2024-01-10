package com.eduardo.AdoptAPetAPI.controllers;

import com.eduardo.AdoptAPetAPI.dto.AnimalDTO;
import com.eduardo.AdoptAPetAPI.services.AnimalService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
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
@Tag(name = "animals", description = "Api para gerenciamento de animais")
public class AnimalController {

    private final AnimalService service;

    @Operation(summary = "Registra um novo animal e o abrigo onde ele está", method = "POST")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Animal registrado com sucesso.")
    })

    @PostMapping
    public ResponseEntity<AnimalDTO> registerAnimalForAdoption(@Valid @RequestBody AnimalDTO dto){
       var animalDto = service.registerAnimalForAdoption(dto);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
                .buildAndExpand(animalDto.getId()).toUri();
        return ResponseEntity.created(uri).body(animalDto);
    }

    @Operation(summary = "Retornar lista paginada dos animais", method = "GET")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista retornada com sucesso")
    })

    @GetMapping("/all")
    public ResponseEntity<Page<AnimalDTO>> getAll(@RequestParam (defaultValue = "0") int page, @RequestParam(defaultValue = "10") int size){
        Page<AnimalDTO> returnAll = service.findAll(page, size);
        return ResponseEntity.ok().body(returnAll);
    }

    @Operation(summary = "Adotar um animal", method = "DELETE")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Animal adotado com sucesso")
    })

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> adoptAnimal(@PathVariable Long id){
        service.adoptAnimal(id);
        return ResponseEntity.noContent().build();
    }
}
