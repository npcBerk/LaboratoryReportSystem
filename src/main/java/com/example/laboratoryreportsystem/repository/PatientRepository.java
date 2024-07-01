package com.example.laboratoryreportsystem.repository;

import com.example.laboratoryreportsystem.entity.Patient;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PatientRepository extends JpaRepository<Patient, Long> {
    List<Patient> findByFirstNameContaining(String firstName);
    List<Patient> findByLastNameContaining(String lastName);
    Patient findByPatientId(String patientId);
}