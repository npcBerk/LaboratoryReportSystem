package com.example.laboratoryreportsystem.dto.request;


import java.util.Date;

public record ReportRequest(
        String fileNumber,
        String patientId,
        String diagnosisTitle,
        String diagnosisDetails,
        Date reportDate,
        String laborantHospitalId

) {

}
