package com.eduardo.AdoptAPetAPI.services;

import com.eduardo.AdoptAPetAPI.entities.User;
import com.eduardo.AdoptAPetAPI.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    public User registerUser(User user){

    }
}
