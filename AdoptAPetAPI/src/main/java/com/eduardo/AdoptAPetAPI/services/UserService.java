package com.eduardo.AdoptAPetAPI.services;

import com.eduardo.AdoptAPetAPI.converter.UserConverter;
import com.eduardo.AdoptAPetAPI.dto.UserDTO;
import com.eduardo.AdoptAPetAPI.entities.User;
import com.eduardo.AdoptAPetAPI.exceptions.EmailNotFoundException;
import com.eduardo.AdoptAPetAPI.exceptions.ResourceNotFoundException;
import com.eduardo.AdoptAPetAPI.exceptions.UserAlreadyRegisteredException;
import com.eduardo.AdoptAPetAPI.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final UserConverter converter;
    @Transactional
    public UserDTO registerUser(UserDTO userDTO)  {
        verifyIfIsAlreadyRegistered(userDTO.getEmail());
        User userToSave = converter.toEntity(userDTO);
        User savedUser = userRepository.save(userToSave);
        return converter.toDTO(savedUser);
    }
    @Transactional(readOnly = true)
    public UserDTO findByEmail(String email){
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new EmailNotFoundException(String.format("O email não pode ser encontrado.", email)));
        return converter.toDTO(user);
    }

    @Transactional(readOnly = true)
    public Page<UserDTO> findAll(int page, int size){
        PageRequest pageRequest = PageRequest.of(page,size);
        Page<User> usersPage = userRepository.findAll(pageRequest);
        return usersPage.map(converter::toDTO);
    }

    @Transactional(propagation = Propagation.SUPPORTS)
    public void deleteUser(Long id){
        if (id == null) {
            throw new IllegalArgumentException("ID não pode ser nulo");
        }
        if (!userRepository.existsById(id)) {
            throw new ResourceNotFoundException("Usuário não pode ser encontrado");
        }

        userRepository.deleteById(id);
    }


    private void verifyIfIsAlreadyRegistered(String email){
        Optional<User> optSavedUser = userRepository.findByEmail(email);
        if (optSavedUser.isPresent()){
            throw new UserAlreadyRegisteredException(String.format("O email %s já está registrado.",email));
        }
    }


}
