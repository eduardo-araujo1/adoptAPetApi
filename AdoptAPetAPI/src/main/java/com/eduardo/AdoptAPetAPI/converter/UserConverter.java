package com.eduardo.AdoptAPetAPI.converter;

import com.eduardo.AdoptAPetAPI.dto.UserDTO;
import com.eduardo.AdoptAPetAPI.entities.User;
import org.springframework.stereotype.Component;

@Component
public class UserConverter {

    public User toEntity(UserDTO userDTO){
        return User.builder()
                .id(userDTO.getId())
                .name(userDTO.getName())
                .email(userDTO.getEmail())
                .isLookingForAnimal(userDTO.isLookingForAnimal())
                .color(userDTO.getColor())
                .size(userDTO.getSize())
                .type(userDTO.getType())
                .build();
    }

    public UserDTO toDTO(User user){
        return UserDTO.builder()
                .id(user.getId())
                .name(user.getName())
                .email(user.getEmail())
                .isLookingForAnimal(user.isLookingForAnimal())
                .color(user.getColor())
                .size(user.getSize())
                .type(user.getType())
                .build();
    }
}
