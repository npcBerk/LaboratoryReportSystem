package com.laboratoryreportsystem.dto.mapper;

import com.laboratoryreportsystem.dto.request.PatientRequest;
import com.laboratoryreportsystem.dto.response.PatientResponse;
import com.laboratoryreportsystem.entity.Patient;
import com.laboratoryreportsystem.entity.Report;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PatientDTOMapper {

    public PatientResponse patientToResponse(Patient patient) {
        return new PatientResponse(
                patient.getId(),
                patient.getFirstName(),
                patient.getLastName(),
                patient.getPatientId(),
                patient.getReports()
                        .stream()
                        .map(Report::getFileNumber)
                        .toList()
        );
    }

    public List<PatientResponse> patientListToResponse(List<Patient> patientList) {
        return patientList.stream()
                .map(this::patientToResponse)
                .toList();
    }

    public Patient requestToPatient(PatientRequest patientRequest) {
        return Patient.builder()
                .firstName(patientRequest.firstName())
                .lastName(patientRequest.lastName())
                .patientId(patientRequest.patientId())
                .build();
    }
}
