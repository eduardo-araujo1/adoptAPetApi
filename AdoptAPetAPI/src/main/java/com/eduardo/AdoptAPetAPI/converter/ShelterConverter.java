package com.eduardo.AdoptAPetAPI.converter;

import com.eduardo.AdoptAPetAPI.dto.ShelterDTO;
import com.eduardo.AdoptAPetAPI.entities.Shelter;
import org.springframework.stereotype.Component;

@Component
public class ShelterConverter {

    public static Shelter toEntity(ShelterDTO shelterDTO) {
        return Shelter.builder()
                .id(shelterDTO.getId())
                .name(shelterDTO.getName())
                .address(shelterDTO.getAddress())
                .number(shelterDTO.getNumber())
                .build();
    }

    public static ShelterDTO toDTO(Shelter shelter) {
        return ShelterDTO.builder()
                .id(shelter.getId())
                .name(shelter.getName())
                .address(shelter.getAddress())
                .number(shelter.getNumber())
                .build();
    }
}
