package com.eduardo.AdoptAPetAPI.repositories;

import com.eduardo.AdoptAPetAPI.entities.Shelter;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ShelterRepository extends JpaRepository<Shelter, Long> {
}
