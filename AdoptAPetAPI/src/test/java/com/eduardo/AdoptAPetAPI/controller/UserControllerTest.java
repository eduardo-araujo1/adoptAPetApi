package com.eduardo.AdoptAPetAPI.controller;

import com.eduardo.AdoptAPetAPI.controllers.UserController;
import com.eduardo.AdoptAPetAPI.dto.UserDTO;
import com.eduardo.AdoptAPetAPI.enums.AnimalColor;
import com.eduardo.AdoptAPetAPI.enums.AnimalSize;
import com.eduardo.AdoptAPetAPI.enums.AnimalType;
import com.eduardo.AdoptAPetAPI.exceptions.UserAlreadyRegisteredException;
import com.eduardo.AdoptAPetAPI.services.UserService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@WebMvcTest(UserController.class)
public class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserService service;


    @Test
    public void TestRegisterUser() throws JsonProcessingException, Exception{
        UserDTO userDTO = new UserDTO(1L,"Joao", "joao@test.com",true, AnimalColor.CARAMEL,
                AnimalSize.SMALL, AnimalType.DOG);

        when(service.registerUser(any())).thenReturn(userDTO);

        mockMvc.perform(post("/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(userDTO)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").exists())
                .andExpect(jsonPath("$.name").value("Joao"))
                .andExpect(jsonPath("$.email").value("joao@test.com"));

        verify(service, times(1)).registerUser(any(UserDTO.class));
    }

    @Test
    public void testRegisterUser_EmailAlreadyExists() throws JsonProcessingException, Exception {
        UserDTO userDTO = new UserDTO(1L, "Joao", "joao@test.com", true, AnimalColor.CARAMEL,
                AnimalSize.SMALL, AnimalType.DOG);

        when(service.registerUser(any())).thenThrow(new UserAlreadyRegisteredException("E-mail já cadastrado"));

        mockMvc.perform(post("/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(userDTO)))
                .andExpect(status().isConflict());

        verify(service, times(1)).registerUser(any(UserDTO.class));
    }

    private String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
