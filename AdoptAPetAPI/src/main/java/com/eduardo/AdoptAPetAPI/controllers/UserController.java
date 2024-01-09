package com.eduardo.AdoptAPetAPI.controllers;

import com.eduardo.AdoptAPetAPI.dto.UserDTO;
import com.eduardo.AdoptAPetAPI.exceptions.UserAlreadyRegisteredException;
import com.eduardo.AdoptAPetAPI.services.UserService;
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
public class UserController {

    private final UserService service;

    @PostMapping
    public ResponseEntity<UserDTO> registerUser(@Valid @RequestBody UserDTO userDto) {
        userDto = service.registerUser(userDto);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
                .buildAndExpand(userDto.getId()).toUri();
        return ResponseEntity.created(uri).body(userDto);
    }

    @GetMapping("/{email}")
    public ResponseEntity<UserDTO> findByEmail(@PathVariable String email) {
        var userDto = service.findByEmail(email);
        return ResponseEntity.ok().body(userDto);
    }

    @GetMapping("/paginated")
    public ResponseEntity<Page<UserDTO>> getUsersPaginated(@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "10") int size) {
        Page<UserDTO> returnUsers = service.findAll(page, size);
        return ResponseEntity.ok().body(returnUsers);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
        service.deleteUser(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
