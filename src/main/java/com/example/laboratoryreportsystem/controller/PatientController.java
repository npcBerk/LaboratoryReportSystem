package com.example.laboratoryreportsystem.controller;

import com.example.laboratoryreportsystem.dto.mapper.PatientDTOMapper;
import com.example.laboratoryreportsystem.dto.request.PatientRequest;
import com.example.laboratoryreportsystem.dto.response.PatientResponse;
import com.example.laboratoryreportsystem.entity.Patient;
import com.example.laboratoryreportsystem.entity.Report;
import com.example.laboratoryreportsystem.service.PatientService;
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
@RequestMapping("/api/patients")
public class PatientController {
    @Autowired
    private final PatientService patientService;
    private final PatientDTOMapper patientDTOMapper;

    public PatientController(PatientService patientService, PatientDTOMapper patientDTOMapper) {
        this.patientService = patientService;
        this.patientDTOMapper = patientDTOMapper;
    }

    @GetMapping
    public ResponseEntity<List<PatientResponse>> getAllPatients() {
        List<Patient> patients = patientService.getAllPatients();
        return ResponseEntity.ok(patientDTOMapper.patientListToResponse(patients));
    }

    @GetMapping("/{id}")
    public ResponseEntity<PatientResponse> getPatientById(@PathVariable Long id) {
        Patient patient = patientService.getPatientById(id);
        return ResponseEntity.ok(patientDTOMapper.patientToResponse(patient));

    }

    @GetMapping("/search")
    public ResponseEntity<List<PatientResponse>> searchPatients(
            @RequestParam(required = false) String firstName,
            @RequestParam(required = false) String lastName,
            @RequestParam(required = false) String patientId) {
        List<Patient> patients = patientService.getAllPatients();
        if (patientId != null) {
            List<Patient> temp = Collections.singletonList(patientService.getPatientByPatientId(patientId));
            patients = patients.stream().filter(temp::contains).collect(Collectors.toList());
        }
        if (firstName != null) {
            List<Patient> temp = patientService.searchPatientsByFirstName(firstName);
            patients = patients.stream().filter(temp::contains).collect(Collectors.toList());
        }
        if (lastName != null) {
            List<Patient> temp = patientService.searchPatientsByLastName(lastName);
            patients = patients.stream().filter(temp::contains).collect(Collectors.toList());
        }
        return ResponseEntity.ok(patientDTOMapper.patientListToResponse(patients)) ;
    }

    @PostMapping
    public ResponseEntity<?> createPatient(@RequestBody PatientRequest request) {
        try {
            Patient toSave = patientDTOMapper.requestToPatient(request);
            List<Report> reports = new ArrayList<>();
            toSave.setReports(reports);
            PatientResponse saved = patientDTOMapper.patientToResponse(patientService.savePatient(toSave));
            return ResponseEntity.created(URI.create("/api/patients/" + saved.id())).body(saved);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updatePatient(
            @PathVariable Long id,
            @RequestParam(required = false) String firstName,
            @RequestParam(required = false) String lastName,
            @RequestParam(required = false) String patientId) {
        try {
            patientService.updatePatient(id, firstName, lastName, patientId);
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deletePatient(@PathVariable Long id) {
        try {
            patientService.deletePatient(id);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }
}
