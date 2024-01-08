package com.eduardo.AdoptAPetAPI.service;

import com.eduardo.AdoptAPetAPI.converter.UserConverter;
import com.eduardo.AdoptAPetAPI.dto.UserDTO;
import com.eduardo.AdoptAPetAPI.entities.User;
import com.eduardo.AdoptAPetAPI.enums.AnimalColor;
import com.eduardo.AdoptAPetAPI.enums.AnimalSize;
import com.eduardo.AdoptAPetAPI.enums.AnimalType;
import com.eduardo.AdoptAPetAPI.exceptions.UserAlreadyRegisteredException;
import com.eduardo.AdoptAPetAPI.repositories.UserRepository;
import com.eduardo.AdoptAPetAPI.services.UserService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    UserConverter userConverter;

    @InjectMocks
    private UserService userService;

    @Test
    public void testRegisterUser(){
        UserDTO userDTO = UserDTO.builder()
                .id(1L)
                .name("Name")
                .email("name@test.com")
                .isLookingForAnimal(true)
                .color(AnimalColor.BLACK)
                .size(AnimalSize.MEDIUM)
                .type(AnimalType.DOG)
                .build();

        when(userRepository.findByEmail("name@test.com")).thenReturn(Optional.empty());
        when(userRepository.save(any(User.class))).thenReturn(User.builder().id(1L).build());
        when(userConverter.toEntity(userDTO)).thenReturn(User.builder().id(1L).build());
        when(userConverter.toDTO(any(User.class))).thenReturn(userDTO);

        UserDTO result = userService.registerUser(userDTO);

        assertNotNull(result);
        assertEquals(1L, result.getId());
        assertEquals("name@test.com", result.getEmail());

        verify(userRepository, times(1)).findByEmail("name@test.com");
        verify(userRepository, times(1)).save(any(User.class));
        verify(userConverter, times(1)).toEntity(userDTO);
        verify(userConverter, times(1)).toDTO(any(User.class));
    }

    @Test
    public void testRegisterUserAlreadyRegistered() {
        UserDTO userDTO = UserDTO.builder()
                .id(1L)
                .name("John Doe")
                .email("john@example.com")
                .isLookingForAnimal(true)
                .color(AnimalColor.BLACK)
                .size(AnimalSize.MEDIUM)
                .type(AnimalType.DOG)
                .build();

        when(userRepository.findByEmail("john@example.com")).thenReturn(Optional.of(User.builder().id(1L).build()));

        assertThrows(UserAlreadyRegisteredException.class, () -> userService.registerUser(userDTO));

        verify(userRepository, times(1)).findByEmail("john@example.com");
        verify(userRepository, never()).save(any(User.class));
        verify(userConverter, never()).toEntity(userDTO);
        verify(userConverter, never()).toDTO(any(User.class));
    }



}
