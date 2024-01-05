package com.eduardo.AdoptAPetAPI.services;

import com.eduardo.AdoptAPetAPI.converter.UserConverter;
import com.eduardo.AdoptAPetAPI.dto.UserDTO;
import com.eduardo.AdoptAPetAPI.entities.User;
import com.eduardo.AdoptAPetAPI.exceptions.UserAlreadyRegisteredException;
import com.eduardo.AdoptAPetAPI.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final UserConverter converter;

    public UserDTO registerUser(UserDTO userDTO)  {
        verifyIfIsAlreadyRegistered(userDTO.getEmail());
        User userToSave = converter.toEntity(userDTO);
        User savedUser = userRepository.save(userToSave);
        return converter.toDTO(savedUser);
    }

    private void verifyIfIsAlreadyRegistered(String email){
        Optional<User> optSavedUser = userRepository.findByEmail(email);
        if (optSavedUser.isPresent()){
            throw new UserAlreadyRegisteredException(String.format("O email %s já está registrado.",email));
        }
    }
}
