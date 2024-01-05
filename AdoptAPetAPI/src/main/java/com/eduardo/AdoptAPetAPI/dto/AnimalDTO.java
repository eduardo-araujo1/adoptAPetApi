package com.eduardo.AdoptAPetAPI.dto;

import com.eduardo.AdoptAPetAPI.enums.AnimalColor;
import com.eduardo.AdoptAPetAPI.enums.AnimalSize;
import com.eduardo.AdoptAPetAPI.enums.AnimalType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@AllArgsConstructor
@Builder
public class AnimalDTO {

   private Long id;
   private String name;
   private ShelterDTO shelter;
   private AnimalType type;
   private AnimalColor color;
   private AnimalSize size;
}
