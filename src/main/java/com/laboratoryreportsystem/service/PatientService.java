package com.laboratoryreportsystem.service;

import com.laboratoryreportsystem.entity.Patient;
import com.laboratoryreportsystem.repository.PatientRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PatientService {
    @Autowired
    private PatientRepository patientRepository;

    public List<Patient> getAllPatients() {
        return patientRepository.findAll();
    }

    public Patient getPatientById(Long id) {
        return patientRepository.findById(id)
                .orElseThrow(EntityNotFoundException::new);
    }

    public Patient getPatientByPatientId(String patientId) {
        return patientRepository.findByPatientId(patientId);
    }

    public List<Patient> searchPatientsByFirstName(String firstName) {
        return patientRepository.findByFirstNameContaining(firstName);
    }

    public List<Patient> searchPatientsByLastName(String lastName) {
        return patientRepository.findByLastNameContaining(lastName);
    }

    @Transactional
    public Patient savePatient(Patient patient) {
        if (patientRepository.existsByPatientId(patient.getPatientId())) {
            throw new IllegalArgumentException("Patient with ID " + patient.getPatientId() + " already exists.");
        }
        return patientRepository.save(patient);
    }

    @Transactional
    public void updatePatient(Long id, String firstName, String lastName, String patientId) {
        Patient patient = patientRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Patient with ID " + id + " not found."));

        if (firstName != null && !firstName.isEmpty() && !firstName.equals(patient.getFirstName())) {
            patient.setFirstName(firstName);
        }
        if (lastName != null && !lastName.isEmpty() && !lastName.equals(patient.getLastName())) {
            patient.setLastName(lastName);
        }
        if (patientId != null && !patientId.isEmpty() && !patientId.equals(patient.getPatientId())) {
            if (patientRepository.existsByPatientId(patientId)) {
                throw new IllegalArgumentException("Patient with ID " + patientId + " already exists.");
            }
            patient.setPatientId(patientId);
        }

        patientRepository.save(patient);
    }

    @Transactional
    public void deletePatient(Long id) {
        if (!patientRepository.existsById(id)) {
            throw new IllegalArgumentException("Patient with ID " + id + " does not exist.");
        }
        patientRepository.deleteById(id);
    }


}
