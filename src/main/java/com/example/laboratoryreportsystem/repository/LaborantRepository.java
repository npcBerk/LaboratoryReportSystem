package com.example.laboratoryreportsystem.repository;

import com.example.laboratoryreportsystem.entity.Laborant;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface LaborantRepository extends JpaRepository<Laborant, Long> {
    List<Laborant> findByFirstNameContaining(String firstName);
    List<Laborant> findByLastNameContaining(String lastName);
    Laborant findByHospitalId(String hospitalId);
}