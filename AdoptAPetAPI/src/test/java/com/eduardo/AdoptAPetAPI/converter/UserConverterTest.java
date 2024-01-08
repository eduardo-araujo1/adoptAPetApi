package com.eduardo.AdoptAPetAPI.converter;

import com.eduardo.AdoptAPetAPI.dto.UserDTO;
import com.eduardo.AdoptAPetAPI.entities.User;
import com.eduardo.AdoptAPetAPI.enums.AnimalColor;
import com.eduardo.AdoptAPetAPI.enums.AnimalSize;
import com.eduardo.AdoptAPetAPI.enums.AnimalType;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
public class UserConverterTest {

    @InjectMocks
    private UserConverter userConverter;

    @Test
    public void testToEntity() {
        UserDTO userDTO = UserDTO.builder()
                .id(1L)
                .name("Name")
                .email("name@example.com")
                .isLookingForAnimal(true)
                .color(AnimalColor.BROWN)
                .size(AnimalSize.MEDIUM)
                .type(AnimalType.DOG)
                .build();

        User user = userConverter.toEntity(userDTO);

        assertEquals(userDTO.getId(), user.getId());
        assertEquals(userDTO.getName(), user.getName());
        assertEquals(userDTO.getEmail(), user.getEmail());
        assertEquals(userDTO.isLookingForAnimal(), user.isLookingForAnimal());
        assertEquals(userDTO.getColor(), user.getColor());
        assertEquals(userDTO.getSize(), user.getSize());
        assertEquals(userDTO.getType(), user.getType());
    }

    @Test
    public void testToDTO() {
        User user = User.builder()
                .id(1L)
                .name("Name")
                .email("name@example.com")
                .isLookingForAnimal(true)
                .color(AnimalColor.BROWN)
                .size(AnimalSize.MEDIUM)
                .type(AnimalType.DOG)
                .build();

        UserDTO userDTO = userConverter.toDTO(user);

        assertEquals(user.getId(), userDTO.getId());
        assertEquals(user.getName(), userDTO.getName());
        assertEquals(user.getEmail(), userDTO.getEmail());
        assertEquals(user.isLookingForAnimal(), userDTO.isLookingForAnimal());
        assertEquals(user.getColor(), userDTO.getColor());
        assertEquals(user.getSize(), userDTO.getSize());
        assertEquals(user.getType(), userDTO.getType());
    }

}
