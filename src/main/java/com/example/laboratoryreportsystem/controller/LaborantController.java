package com.example.laboratoryreportsystem.controller;

import com.example.laboratoryreportsystem.entity.Laborant;
import com.example.laboratoryreportsystem.service.LaborantService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/laborants")
public class LaborantController {
    @Autowired
    private LaborantService laborantService;

    @GetMapping
    public List<Laborant> getAllLaborants() {
        return laborantService.getAllLaborants();
    }

    @GetMapping("/{id}")
    public Laborant getLaborantById(@PathVariable Long id) {
        return laborantService.getLaborantById(id);
    }

    @GetMapping("/search")
    public List<Laborant> searchLaborants(
            @RequestParam(required = false) String firstName,
            @RequestParam(required = false) String lastName,
            @RequestParam(required = false) String hospitalId) {
        if (hospitalId != null) {
            return List.of(laborantService.getLaborantByHospitalId(hospitalId));
        }
        if (firstName != null) {
            return laborantService.searchLaborantsByFirstName(firstName);
        }
        if (lastName != null) {
            return laborantService.searchLaborantsByLastName(lastName);
        }
        return laborantService.getAllLaborants();
    }

    @PostMapping
    public Laborant createLaborant(@RequestBody Laborant laborant) {
        return laborantService.saveLaborant(laborant);
    }

    @PutMapping("/{id}")
    public Laborant updateLaborant(@PathVariable Long id, @RequestBody Laborant laborant) {
        laborant.setId(id);
        return laborantService.saveLaborant(laborant);
    }

    @DeleteMapping("/{id}")
    public void deleteLaborant(@PathVariable Long id) {
        laborantService.deleteLaborant(id);
    }
}
