package com.eduardo.AdoptAPetAPI.service;

import com.eduardo.AdoptAPetAPI.converter.UserConverter;
import com.eduardo.AdoptAPetAPI.dto.UserDTO;
import com.eduardo.AdoptAPetAPI.entities.User;
import com.eduardo.AdoptAPetAPI.enums.AnimalColor;
import com.eduardo.AdoptAPetAPI.enums.AnimalSize;
import com.eduardo.AdoptAPetAPI.enums.AnimalType;
import com.eduardo.AdoptAPetAPI.exceptions.EmailNotFoundException;
import com.eduardo.AdoptAPetAPI.exceptions.ResourceNotFoundException;
import com.eduardo.AdoptAPetAPI.exceptions.UserAlreadyRegisteredException;
import com.eduardo.AdoptAPetAPI.repositories.UserRepository;
import com.eduardo.AdoptAPetAPI.services.UserService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
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
    public void testRegisterUser() {
        // Arrange
        UserDTO userDTO = UserDTO.builder()
                .id(1L)
                .name("Name")
                .email("name@test.com")
                .isLookingForAnimal(true)
                .color(AnimalColor.BLACK)
                .size(AnimalSize.MEDIUM)
                .type(AnimalType.DOG)
                .build();

        User userEntity = User.builder().id(1L).build();

        when(userRepository.findByEmail("name@test.com")).thenReturn(Optional.empty());
        when(userRepository.save(any(User.class))).thenReturn(userEntity);
        when(userConverter.toEntity(userDTO)).thenReturn(userEntity);
        when(userConverter.toDTO(userEntity)).thenReturn(userDTO);

        // Act
        UserDTO result = userService.registerUser(userDTO);

        // Assert
        assertNotNull(result);
        assertEquals(1L, result.getId());
        assertEquals("name@test.com", result.getEmail());

        verify(userRepository, times(1)).findByEmail("name@test.com");
        verify(userRepository, times(1)).save(any(User.class));
        verify(userConverter, times(1)).toEntity(userDTO);
        verify(userConverter, times(1)).toDTO(userEntity);
    }

    @Test
    public void testRegisterUserAlreadyRegistered() {
        // Arrange
        UserDTO userDTO = UserDTO.builder()
                .id(1L)
                .name("Name")
                .email("name@test.com")
                .isLookingForAnimal(true)
                .color(AnimalColor.BLACK)
                .size(AnimalSize.MEDIUM)
                .type(AnimalType.DOG)
                .build();

        when(userRepository.findByEmail("name@test.com"))
                .thenReturn(Optional.of(User.builder().id(1L).build()));

        // Act & Assert
        assertThrows(UserAlreadyRegisteredException.class, () -> userService.registerUser(userDTO));

        // Verify
        verify(userRepository, times(1)).findByEmail("name@test.com");
        verify(userRepository, never()).save(any(User.class));
        verify(userConverter, never()).toEntity(userDTO);
        verify(userConverter, never()).toDTO(any(User.class));
    }

    @Test
    public void testFindByEmail_UserFound() {
        // Arrange
        String emailToFind = "user@example.com";
        User existingUser = User.builder()
                .id(1L)
                .name("Example")
                .email(emailToFind)
                .isLookingForAnimal(true)
                .color(AnimalColor.BROWN)
                .size(AnimalSize.MEDIUM)
                .type(AnimalType.DOG)
                .build();

        when(userRepository.findByEmail(emailToFind)).thenReturn(Optional.of(existingUser));

        UserDTO expectedUserDTO = UserDTO.builder()
                .id(1L)
                .name("Example")
                .email(emailToFind)
                .isLookingForAnimal(true)
                .color(AnimalColor.BROWN)
                .size(AnimalSize.MEDIUM)
                .type(AnimalType.DOG)
                .build();

        when(userConverter.toDTO(existingUser)).thenReturn(expectedUserDTO);

        // Act
        UserDTO result = userService.findByEmail(emailToFind);

        // Assert
        assertNotNull(result);
        assertEquals(expectedUserDTO, result);

        // Verify
        verify(userRepository, times(1)).findByEmail(emailToFind);
        verify(userConverter, times(1)).toDTO(existingUser);
    }

    @Test
    public void testFindByEmail_UserNotFound() {
        // Arrange
        String emailToFind = "noexist@example.com";
        when(userRepository.findByEmail(emailToFind)).thenReturn(Optional.empty());

        // Act and Assert
        assertThrows(EmailNotFoundException.class, () -> userService.findByEmail(emailToFind));

        // Verify
        verify(userRepository, times(1)).findByEmail(emailToFind);
        verify(userConverter, never()).toDTO(any());
    }

    @Test
    public void testFindAll() {
        // Arrange
        int page = 0;
        int size = 10;

        List<User> userList = Arrays.asList(
                User.builder().id(1L).name("User 1").email("user1@example.com").build(),
                User.builder().id(2L).name("User 2").email("user2@example.com").build());

        Page<User> userPage = new PageImpl<>(userList);

        when(userRepository.findAll(PageRequest.of(page, size))).thenReturn(userPage);

        List<UserDTO> userDTOList = Arrays.asList(
                UserDTO.builder().id(1L).name("User 1").email("user1@example.com").build(),
                UserDTO.builder().id(2L).name("User 2").email("user2@example.com").build());

        when(userConverter.toDTO(any(User.class)))
                .thenAnswer(invocation -> {
                    User user = invocation.getArgument(0);
                    return userDTOList.stream()
                            .filter(dto -> dto.getId().equals(user.getId()))
                            .findFirst()
                            .orElse(null);
                });

        // Act
        Page<UserDTO> result = userService.findAll(page, size);

        // Assert
        assertNotNull(result);
        assertEquals(userDTOList.size(), result.getContent().size());
        assertTrue(result.getContent().containsAll(userDTOList));

        // Verify
        verify(userRepository, times(1)).findAll(PageRequest.of(page, size));
        verify(userConverter, times(userList.size())).toDTO(any(User.class));
    }

    @Test
    public void testDeleteUserById() {
        // Arrange
        Long userId = 1L;
        when(userRepository.existsById(userId)).thenReturn(true);

        // Act
        userService.deleteUser(userId);

        // Assert and Verify
        verify(userRepository, times(1)).deleteById(userId);
        verify(userRepository, times(1)).existsById(userId);
    }

    @Test
    public void testDeleteNonExistingUser() {
        // Arrange
        Long userId = 1L;
        when(userRepository.existsById(userId)).thenReturn(false);

        // Act and Assert
        assertThrows(ResourceNotFoundException.class, () -> userService.deleteUser(userId));

        // Verify
        verify(userRepository, never()).deleteById(userId);
        verify(userRepository, times(1)).existsById(userId);
    }

    @Test
    public void retireInterestToAdopt_ShouldUpdateUserAndReturnDTO() {
        Long userId = 1L;
        User existingUser = new User(userId, "Test User", "test@email.com", true, AnimalColor.BLACK, AnimalSize.MEDIUM, AnimalType.DOG);
        UserDTO expectedDTO = new UserDTO(userId, "Test User", "test@email.com", false, AnimalColor.NONE, AnimalSize.NONE, AnimalType.NONE);

        when(userRepository.findById(userId)).thenReturn(java.util.Optional.of(existingUser));
        when(userRepository.save(any(User.class))).thenReturn(existingUser);
        when(userConverter.toDTO(existingUser)).thenReturn(expectedDTO);

        UserDTO updatedDTO = userService.retireInterestToAdopt(userId);

        assertThat(updatedDTO).isEqualTo(expectedDTO);
        assertThat(existingUser.isLookingForAnimal()).isFalse();
        assertThat(existingUser.getColor()).isEqualTo(AnimalColor.NONE);
        assertThat(existingUser.getSize()).isEqualTo(AnimalSize.NONE);
        assertThat(existingUser.getType()).isEqualTo(AnimalType.NONE);
    }

    @Test
    public void retireInterestToAdopt_ShouldThrowResourceNotFoundExceptionWhenUserNotFound() {
        Long userId = 2L;
        when(userRepository.findById(userId)).thenReturn(java.util.Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> userService.retireInterestToAdopt(userId));
    }

}
