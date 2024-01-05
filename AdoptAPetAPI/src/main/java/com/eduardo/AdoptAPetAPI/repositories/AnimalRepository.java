package com.eduardo.AdoptAPetAPI.repositories;

import com.eduardo.AdoptAPetAPI.entities.Animal;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AnimalRepository extends JpaRepository<Animal,Long> {
}
