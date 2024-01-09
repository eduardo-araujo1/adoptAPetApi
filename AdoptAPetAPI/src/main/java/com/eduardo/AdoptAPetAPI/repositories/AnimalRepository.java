package com.eduardo.AdoptAPetAPI.repositories;

import com.eduardo.AdoptAPetAPI.entities.Animal;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AnimalRepository extends JpaRepository<Animal,Long> {

}

