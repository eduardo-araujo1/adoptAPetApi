package com.eduardo.AdoptAPetAPI.controller;

import com.eduardo.AdoptAPetAPI.controllers.UserController;
import com.eduardo.AdoptAPetAPI.dto.UserDTO;
import com.eduardo.AdoptAPetAPI.enums.AnimalColor;
import com.eduardo.AdoptAPetAPI.enums.AnimalSize;
import com.eduardo.AdoptAPetAPI.enums.AnimalType;
import com.eduardo.AdoptAPetAPI.exceptions.EmailNotFoundException;
import com.eduardo.AdoptAPetAPI.exceptions.ResourceNotFoundException;
import com.eduardo.AdoptAPetAPI.exceptions.UserAlreadyRegisteredException;
import com.eduardo.AdoptAPetAPI.services.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@WebMvcTest(UserController.class)
public class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserService service;



    @Test
    public void TestRegisterUser() throws Exception{
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
    public void testRegisterUser_EmailAlreadyExists() throws Exception {
        UserDTO userDTO = new UserDTO(1L, "Joao", "joao@test.com", true, AnimalColor.CARAMEL,
                AnimalSize.SMALL, AnimalType.DOG);

        when(service.registerUser(any())).thenThrow(new UserAlreadyRegisteredException("E-mail já cadastrado"));

        mockMvc.perform(post("/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(userDTO)))
                .andExpect(status().isConflict());

        verify(service, times(1)).registerUser(any(UserDTO.class));
    }

    @Test
    public void testFindByEmail_UserFound() throws Exception {
        String userEmail = "Teste@test.com";
        UserDTO userDTO = new UserDTO(1L, "User", userEmail, true, AnimalColor.BLACK,
                AnimalSize.MEDIUM, AnimalType.DOG);

        when(service.findByEmail(userEmail)).thenReturn(userDTO);

        mockMvc.perform(get("/users/{email}", userEmail)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(userDTO.getId()))
                .andExpect(jsonPath("$.name").value(userDTO.getName()))
                .andExpect(jsonPath("$.email").value(userDTO.getEmail()));

        verify(service, times(1)).findByEmail(userEmail);
    }

    @Test
    public void testFindByEmail_UserNotFound() throws Exception {
        String userEmail = "noexistent@test.com";

        when(service.findByEmail(userEmail)).thenThrow(new EmailNotFoundException("Usuário não encontrado"));

        mockMvc.perform(get("/users/{email}", userEmail)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());

        verify(service, times(1)).findByEmail(userEmail);
    }

    @Test
    public void testGetUsersPaginated_Success() throws Exception {
        List<UserDTO> usersDTO = Arrays.asList(
                new UserDTO(1L, "Eduardo", "eduardo@email.com", true, AnimalColor.BROWN, AnimalSize.SMALL, AnimalType.HAMSTER),
                new UserDTO(2L, "Maria", "maria@email.com", false, AnimalColor.BROWN, AnimalSize.SMALL, AnimalType.HAMSTER)
        );


        when(service.findAll(0, 10)).thenReturn(new PageImpl<>(usersDTO, PageRequest.of(0, 10), 2));


        mockMvc.perform(get("/users/paginated?page=0&size=10"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content").isArray())
                .andExpect(jsonPath("$.content.length()").value(2));
    }

    @Test
    public void testDeleteUser_Success() throws Exception {
        Long userId = 1L;

        doNothing().when(service).deleteUser(userId);

        mockMvc.perform(delete("/users/{id}", userId))
                .andExpect(status().isNoContent());

        verify(service, times(1)).deleteUser(userId);
    }

    @Test
    public void testRetireInterestToAdopt_Success() throws Exception {
        UserDTO userDTO = new UserDTO(1L, "Joao", "joao@test.com", true, AnimalColor.CARAMEL,
                AnimalSize.SMALL, AnimalType.DOG);

        when(service.retireInterestToAdopt(userDTO.getId())).thenReturn(userDTO);

        mockMvc.perform(put("/users/no-adopt/{id}", userDTO.getId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(userDTO.getId()))
                .andExpect(jsonPath("$.name").value(userDTO.getName()))
                .andExpect(jsonPath("$.email").value(userDTO.getEmail()));


        verify(service, times(1)).retireInterestToAdopt(userDTO.getId());
    }

    @Test
    public void testRetireInterestToAdopt_UserNotFound() throws Exception {
        Long userId = 1L;

        when(service.retireInterestToAdopt(userId)).thenThrow(new ResourceNotFoundException("Usuário não encontrado"));

        mockMvc.perform(put("/users/no-adopt/{id}", userId))
                .andExpect(status().isNotFound());


        verify(service, times(1)).retireInterestToAdopt(userId);
    }


    private String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
