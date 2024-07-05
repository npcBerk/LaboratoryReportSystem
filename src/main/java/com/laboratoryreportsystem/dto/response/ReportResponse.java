package com.laboratoryreportsystem.dto.response;


import java.util.Date;


public record ReportResponse(
        Long id,
        String fileNumber,
        String patientFirstName,
        String patientLastName,
        String patientId,
        String diagnosisTitle,
        String diagnosisDetails,
        Date reportDate,
        String laborantFirstName,
        String laborantLastName,
        String laborantHospitalId,
        String image
){

}
