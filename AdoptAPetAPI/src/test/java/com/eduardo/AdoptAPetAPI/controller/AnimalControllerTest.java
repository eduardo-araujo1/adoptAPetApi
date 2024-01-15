package com.eduardo.AdoptAPetAPI.controller;

import com.eduardo.AdoptAPetAPI.controllers.AnimalController;
import com.eduardo.AdoptAPetAPI.dto.AnimalDTO;
import com.eduardo.AdoptAPetAPI.dto.ShelterDTO;
import com.eduardo.AdoptAPetAPI.enums.AnimalColor;
import com.eduardo.AdoptAPetAPI.enums.AnimalSize;
import com.eduardo.AdoptAPetAPI.enums.AnimalType;
import com.eduardo.AdoptAPetAPI.services.AnimalService;
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


@WebMvcTest(AnimalController.class)
public class AnimalControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private AnimalService service;

    @Test
    public void testRegisterAnimalForAdoption_Success() throws Exception {
        AnimalDTO animalDTO = AnimalDTO.builder()
                .id(1L)
                .breed("Golden Retriever")
                .type(AnimalType.DOG)
                .color(AnimalColor.CARAMEL)
                .size(AnimalSize.MEDIUM)
                .shelter(ShelterDTO.builder()
                        .name("Shelter Name")
                        .address("123 Shelter St")
                        .number("456")
                        .build())
                .build();

        when(service.registerAnimalForAdoption(any(AnimalDTO.class))).thenReturn(animalDTO);


        mockMvc.perform(post("/animals")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(animalDTO)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(animalDTO.getId()))
                .andExpect(jsonPath("$.breed").value(animalDTO.getBreed()))
                .andExpect(jsonPath("$.type").value(animalDTO.getType().name()))
                .andExpect(jsonPath("$.color").value(animalDTO.getColor().name()))
                .andExpect(jsonPath("$.size").value(animalDTO.getSize().name()))
                .andExpect(jsonPath("$.shelter.name").value(animalDTO.getShelter().getName()))
                .andExpect(jsonPath("$.shelter.address").value(animalDTO.getShelter().getAddress()))
                .andExpect(jsonPath("$.shelter.number").value(animalDTO.getShelter().getNumber()));


        verify(service, times(1)).registerAnimalForAdoption(any(AnimalDTO.class));
    }



    private String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
