package com.example.laboratoryreportsystem.service;

import com.example.laboratoryreportsystem.entity.Laborant;
import com.example.laboratoryreportsystem.repository.LaborantRepository;
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
        return laborantRepository.findById(id).orElse(null);
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

    public Laborant saveLaborant(Laborant laborant) {
        return laborantRepository.save(laborant);
    }

    public void deleteLaborant(Long id) {
        laborantRepository.deleteById(id);
    }
}
