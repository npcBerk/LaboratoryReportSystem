package com.laboratoryreportsystem.dto.mapper;

import com.laboratoryreportsystem.dto.request.ReportRequest;
import com.laboratoryreportsystem.dto.response.ReportResponse;
import com.laboratoryreportsystem.entity.Report;
import com.laboratoryreportsystem.service.LaborantService;
import com.laboratoryreportsystem.service.PatientService;
import com.laboratoryreportsystem.service.ReportService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReportDTOMapper {


    private final ReportService reportService;
    private final PatientService patientService;
    private final LaborantService laborantService;

    public ReportDTOMapper(ReportService reportService, PatientService patientService, LaborantService laborantService) {
        this.reportService = reportService;
        this.patientService = patientService;
        this.laborantService = laborantService;
    }

    public ReportResponse reportToResponse(Report report) {
        return new ReportResponse(
                report.getId(),
                report.getFileNumber(),
                report.getPatient().getFirstName(),
                report.getPatient().getLastName(),
                report.getPatient().getPatientId(),
                report.getDiagnosisTitle(),
                report.getDiagnosisDetails(),
                report.getReportDate(),
                report.getLaborant().getFirstName(),
                report.getLaborant().getLastName(),
                report.getLaborant().getHospitalId(),
                "/api/reports/" + report.getId() + "/image"

        );
    }
    public List<ReportResponse> reportListToResponse(List<Report> reportList) {
        return reportList.stream()
                .map(this::reportToResponse)
                .toList();
    }

    public Report requestToReport(ReportRequest reportRequest) {
        return Report.builder()
                .fileNumber(reportRequest.fileNumber())
                .patient(patientService.getPatientByPatientId(reportRequest.patientId()))
                .diagnosisTitle(reportRequest.diagnosisTitle())
                .diagnosisDetails(reportRequest.diagnosisDetails())
                .reportDate(reportRequest.reportDate())
                .laborant(laborantService.getLaborantByHospitalId(reportRequest.laborantHospitalId()))
                .build();
    }
}
