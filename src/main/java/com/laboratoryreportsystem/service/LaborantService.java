package com.laboratoryreportsystem.service;

import com.laboratoryreportsystem.entity.Laborant;
import com.laboratoryreportsystem.repository.LaborantRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LaborantService {
    @Autowired
    private LaborantRepository laborantRepository;

    public List<Laborant> getAllLaborants() {
        return laborantRepository.findAll();
    }

    public Laborant getLaborantById(Long id) {
        return laborantRepository.findById(id)
                .orElseThrow(()-> new EntityNotFoundException("Laborant not found"));
    }

    public Laborant getLaborantByHospitalId(String hospitalId) {
        return laborantRepository.findByHospitalId(hospitalId);
    }

    public List<Laborant> searchLaborantsByFirstName(String firstName) {
        return laborantRepository.findByFirstNameContaining(firstName);
    }

    public List<Laborant> searchLaborantsByLastName(String lastName) {
        return laborantRepository.findByLastNameContaining(lastName);
    }

    @Transactional
    public Laborant saveLaborant(Laborant laborant) {
        if (laborantRepository.existsByHospitalId(laborant.getHospitalId())) {
            throw new IllegalArgumentException("Laborant with ID " + laborant.getHospitalId() + " already exists.");
        }
        return laborantRepository.save(laborant);
    }

    @Transactional
    public void updateLaborant(Long id, String firstName, String lastName, String hospitalId) {
        Laborant laborant = laborantRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Laborant with ID " + id + " not found."));

        if (firstName != null && !firstName.isEmpty() && !firstName.equals(laborant.getFirstName())) {
            laborant.setFirstName(firstName);
        }
        if (lastName != null && !lastName.isEmpty() && !lastName.equals(laborant.getLastName())) {
            laborant.setLastName(lastName);
        }
        if (hospitalId != null && !hospitalId.isEmpty() && !hospitalId.equals(laborant.getHospitalId())) {
            if (laborantRepository.existsByHospitalId(hospitalId)) {
                throw new IllegalArgumentException("Laborant with hospital ID " + hospitalId + " already exists.");
            }
            laborant.setHospitalId(hospitalId);
        }

        laborantRepository.save(laborant);
    }

    @Transactional
    public void deleteLaborant(Long id) {
        if (!laborantRepository.existsById(id)) {
            throw new IllegalArgumentException("Laborant with ID " + id + " does not exist.");
        }
        laborantRepository.deleteById(id);
    }
}
