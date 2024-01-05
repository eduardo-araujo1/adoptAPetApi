package com.eduardo.AdoptAPetAPI.repositories;

import com.eduardo.AdoptAPetAPI.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository <User, Long> {
}
