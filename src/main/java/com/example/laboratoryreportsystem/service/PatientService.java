package com.example.laboratoryreportsystem.service;

import com.example.laboratoryreportsystem.entity.Patient;
import com.example.laboratoryreportsystem.repository.PatientRepository;
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
        return patientRepository.findById(id).orElse(null);
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

    public Patient savePatient(Patient patient) {
        return patientRepository.save(patient);
    }

    public void deletePatient(Long id) {
        patientRepository.deleteById(id);
    }
}
