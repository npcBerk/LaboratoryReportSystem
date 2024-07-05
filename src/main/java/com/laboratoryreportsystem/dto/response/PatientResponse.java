package com.laboratoryreportsystem.dto.response;

import java.util.List;

public record PatientResponse(
        Long id,
        String firstName,
        String lastName,
        String patientId,
        List<String> reportsFileNumber
){
}
