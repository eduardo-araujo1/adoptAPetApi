package com.eduardo.AdoptAPetAPI.controllers;

import com.eduardo.AdoptAPetAPI.dto.UserDTO;
import com.eduardo.AdoptAPetAPI.exceptions.UserAlreadyRegisteredException;
import com.eduardo.AdoptAPetAPI.services.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@RestController
@RequiredArgsConstructor
@RequestMapping("/users")
@Tag(name = "users", description = "Api para gerenciamento de usuários")
public class UserController {

    private final UserService service;

    @Operation(summary = "Registra um novo usuário", method = "POST")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Usuário criado com sucesso"),
            @ApiResponse(responseCode = "409", description = "Email já cadastrado")

    })

    @PostMapping
    public ResponseEntity<UserDTO> registerUser(@Valid @RequestBody UserDTO userDto) {
        userDto = service.registerUser(userDto);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
                .buildAndExpand(userDto.getId()).toUri();
        return ResponseEntity.created(uri).body(userDto);
    }

    @Operation(summary = "Busca um usuário pelo email", method = "GET")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Usuário encontrado com sucesso"),
            @ApiResponse(responseCode = "404", description = "Usuário não encontrado")
    })

    @GetMapping("/{email}")
    public ResponseEntity<UserDTO> findByEmail(@PathVariable String email) {
        var userDto = service.findByEmail(email);
        return ResponseEntity.ok().body(userDto);
    }

    @Operation(summary = "Lista paginada de usuários", method = "GET")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista com todos usuarios cadastrados")
    })

    @GetMapping("/paginated")
    public ResponseEntity<Page<UserDTO>> getUsersPaginated(@RequestParam(defaultValue = "0") int page,
                                                           @RequestParam(defaultValue = "10") int size) {
        Page<UserDTO> returnUsers = service.findAll(page, size);
        return ResponseEntity.ok().body(returnUsers);
    }

    @Operation(summary = "Deletar um usuário", method = "DELETE")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Usuário deletado com sucesso"),
            @ApiResponse(responseCode = "400", description = "Requisição inválida"),
            @ApiResponse(responseCode = "404", description = "Usuário não encontrado"),
    })

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
        service.deleteUser(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @Operation(summary = "Retirar o interesse em adotar um animal", method = "PUT")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Usuario atualizado com sucesso"),
            @ApiResponse(responseCode = "404", description = "Usuário não foi encontrado")
    })

    @PutMapping("/no-adopt/{id}")
    public ResponseEntity<UserDTO> retireInterestToAdopt(@PathVariable Long id){
        UserDTO updateUser = service.retireInterestToAdopt(id);
        return ResponseEntity.ok().body(updateUser);
    }
}
