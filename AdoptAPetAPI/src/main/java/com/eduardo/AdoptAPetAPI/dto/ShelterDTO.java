package com.eduardo.AdoptAPetAPI.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@AllArgsConstructor
@Builder
public class ShelterDTO {

    private Long id;
    private String name;
    private String address;
    private String number;
}
