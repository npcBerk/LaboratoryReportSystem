package com.example.laboratoryreportsystem.dto.request;

public record PatientRequest(
        String firstName,
        String lastName,
        String patientId
) {
}
