package com.laboratoryreportsystem.controller;

import com.laboratoryreportsystem.dto.response.ReportResponse;
import com.laboratoryreportsystem.dto.mapper.ReportDTOMapper;
import com.laboratoryreportsystem.dto.request.ReportRequest;
import com.laboratoryreportsystem.entity.Report;
import com.laboratoryreportsystem.service.ReportService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.net.URI;
import java.util.*;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/reports")
public class ReportController {
    @Autowired
    private final ReportService reportService;
    private final ReportDTOMapper reportDTOMapper;

    public ReportController(ReportService reportService, ReportDTOMapper reportDTOMapper) {
        this.reportService = reportService;
        this.reportDTOMapper = reportDTOMapper;
    }

    @GetMapping
    public ResponseEntity<List<ReportResponse>> getAllReports() {
        List<Report> reports = reportService.getAllReports();
        return ResponseEntity.ok(reportDTOMapper.reportListToResponse(reports));
    }


    @GetMapping("/{id}")
    public ResponseEntity<ReportResponse> getReportById(@PathVariable Long id) {
        Report report = reportService.getReportById(id);
        return ResponseEntity.ok(reportDTOMapper.reportToResponse(report));
    }

    @GetMapping("/sorted-by-date")
    public ResponseEntity<List<ReportResponse>> getAllReportsSortedByDate(@RequestParam(defaultValue = "ASC") String direction) {
        Sort.Direction sortDirection = Sort.Direction.fromString(direction.toUpperCase());
        List<Report> sortedReports = reportService.getAllReportsSortedByDate(sortDirection);
        List<ReportResponse> response = reportDTOMapper.reportListToResponse(sortedReports);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/search")
    public ResponseEntity<List<ReportResponse>> searchReports(
            @RequestParam(required = false) String patientFirstName,
            @RequestParam(required = false) String patientLastName,
            @RequestParam(required = false) String patientId,
            @RequestParam(required = false) String laborantFirstName,
            @RequestParam(required = false) String laborantLastName,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Date reportDate) {
        List<Report> reports = reportService.getAllReports();
        if (patientFirstName != null) {
            List<Report> temp = reportService.searchReportsByPatientFirstName(patientFirstName);
            reports = reports.stream().filter(temp::contains).collect(Collectors.toList());
        }
        if (patientLastName != null) {
            List<Report> temp = reportService.searchReportsByPatientLastName(patientLastName);
            reports = reports.stream().filter(temp::contains).collect(Collectors.toList());
        }
        if (patientId != null) {
            List<Report> temp = reportService.searchReportsByPatientId(patientId);
            reports = reports.stream().filter(temp::contains).collect(Collectors.toList());
        }
        if (laborantFirstName != null) {
            List<Report> temp = reportService.searchReportsByLaborantFirstName(laborantFirstName);
            reports = reports.stream().filter(temp::contains).collect(Collectors.toList());
        }
        if (laborantLastName != null) {
            List<Report> temp = reportService.searchReportsByLaborantLastName(laborantLastName);
            reports = reports.stream().filter(temp::contains).collect(Collectors.toList());
        }
        if (reportDate != null) {
            List<Report> temp = reportService.getReportsByDate(reportDate);
            reports = reports.stream().filter(temp::contains).collect(Collectors.toList());
        }
        return ResponseEntity.ok(reportDTOMapper.reportListToResponse(reports));
    }

    @PostMapping(consumes = { MediaType.MULTIPART_FORM_DATA_VALUE })
    public ResponseEntity<?> createReport(@RequestPart("report") String reportJson, @RequestPart("image") MultipartFile file) {
        try {
            // JSON String'i ReportRequest nesnesine dönüştürme
            ObjectMapper objectMapper = new ObjectMapper();
            ReportRequest request = objectMapper.readValue(reportJson, ReportRequest.class);

            // Report nesnesini oluşturma
            Report toSave = reportDTOMapper.requestToReport(request);
            String imageBase64 = Base64.getEncoder().encodeToString(file.getBytes());
            toSave.setImageBase64(imageBase64);

            // Report kaydetme
            ReportResponse saved = reportDTOMapper.reportToResponse(reportService.saveReport(toSave));
            return ResponseEntity.created(URI.create("/api/reports/" + saved.id())).body(saved);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }
    @PutMapping("/{id}")
    public ResponseEntity<?> updateReport(
            @PathVariable Long id,
            @RequestParam(required = false) String fileNumber,
            @RequestParam(required = false) String patientId,
            @RequestParam(required = false) String diagnosisTitle,
            @RequestParam(required = false) String diagnosisDetails,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Date reportDate,
            @RequestParam(required = false) String laborantHospitalId) {
        try {
            reportService.updateReport(id, fileNumber, patientId, diagnosisTitle, diagnosisDetails, reportDate, laborantHospitalId);
            return ResponseEntity.ok(reportDTOMapper.reportToResponse(reportService.getReportById(id)));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteReport(@PathVariable Long id) {
        try {
            reportService.deleteReport(id);
            return ResponseEntity.ok(HttpStatus.GONE);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @GetMapping("/{id}/image")
    public ResponseEntity<byte[]> getReportImageById(@PathVariable Long id) {
        Optional<Report> report = Optional.ofNullable(reportService.getReportById(id));
        if (report.isPresent() && report.get().getImageBase64() != null) {
            byte[] imageBytes = Base64.getDecoder().decode(report.get().getImageBase64());
            return ResponseEntity.ok()
                    .contentType(MediaType.IMAGE_PNG)
                    .body(imageBytes);
        } else {
            return ResponseEntity.notFound().build();
        }
    }


}
