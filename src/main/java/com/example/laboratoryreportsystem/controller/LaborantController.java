package com.example.laboratoryreportsystem.controller;

import com.example.laboratoryreportsystem.dto.mapper.LaborantDTOMapper;
import com.example.laboratoryreportsystem.dto.response.LaborantResponse;
import com.example.laboratoryreportsystem.dto.request.LaborantRequest;
import com.example.laboratoryreportsystem.entity.Laborant;
import com.example.laboratoryreportsystem.entity.Report;
import com.example.laboratoryreportsystem.service.LaborantService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/laborants")
public class LaborantController {
    @Autowired
    private final LaborantService laborantService;
    private final LaborantDTOMapper laborantDTOMapper;

    public LaborantController(LaborantService laborantService, LaborantDTOMapper laborantDTOMapper) {
        this.laborantService = laborantService;
        this.laborantDTOMapper = laborantDTOMapper;
    }

    @GetMapping
    public ResponseEntity<List<LaborantResponse>> getAllLaborants() {
        List<Laborant> laborants = laborantService.getAllLaborants();
        return ResponseEntity.ok(laborantDTOMapper.laborantListToResponse(laborants));
    }

    @GetMapping("/{id}")
    public ResponseEntity<LaborantResponse> getLaborantById(@PathVariable Long id) {
        Laborant laborant = laborantService.getLaborantById(id);
        return ResponseEntity.ok(laborantDTOMapper.laborantToResponse(laborant));
    }

    @GetMapping("/search")
    public ResponseEntity<List<LaborantResponse>> searchLaborants(
            @RequestParam(required = false) String firstName,
            @RequestParam(required = false) String lastName,
            @RequestParam(required = false) String hospitalId) {
        List<Laborant> laborants = laborantService.getAllLaborants();
        if (hospitalId != null) {
            List<Laborant> temp = Collections.singletonList(laborantService.getLaborantByHospitalId(hospitalId));
            laborants = laborants.stream().filter(temp::contains).collect(Collectors.toList());
        }
        if (firstName != null) {
            List<Laborant> temp = laborantService.searchLaborantsByFirstName(firstName);
            laborants = laborants.stream().filter(temp::contains).collect(Collectors.toList());
        }
        if (lastName != null) {
            List<Laborant> temp = laborantService.searchLaborantsByLastName(lastName);
            laborants = laborants.stream().filter(temp::contains).collect(Collectors.toList());
        }
        return ResponseEntity.ok(laborantDTOMapper.laborantListToResponse(laborants));
    }

    @PostMapping
    public ResponseEntity<?> createLaborant(@RequestBody LaborantRequest request) {
        try {
            Laborant toSave = laborantDTOMapper.requestToLaborant(request);
            List<Report> reports = new ArrayList<>();
            toSave.setReports(reports);
            LaborantResponse saved = laborantDTOMapper.laborantToResponse(laborantService.saveLaborant(toSave));
            return ResponseEntity.created(URI.create("/api/laborants/" + saved.id())).body(saved);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateLaborant(
            @PathVariable Long id,
            @RequestParam(required = false) String firstName,
            @RequestParam(required = false) String lastName,
            @RequestParam(required = false) String hospitalId) {
        try {
            laborantService.updateLaborant(id, firstName, lastName, hospitalId);
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteLaborant(@PathVariable Long id) {
        try {
            laborantService.deleteLaborant(id);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }
}
