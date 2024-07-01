package com.example.laboratoryreportsystem.controller;

import com.example.laboratoryreportsystem.entity.Report;
import com.example.laboratoryreportsystem.service.ReportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/api/reports")
public class ReportController {
    @Autowired
    private ReportService reportService;

    @GetMapping
    public List<Report> getAllReports() {
        return reportService.getAllReports();
    }

    @GetMapping("/{id}")
    public Report getReportById(@PathVariable Long id) {
        return reportService.getReportById(id);
    }

    @GetMapping("/search")
    public List<Report> searchReports(
            @RequestParam(required = false) String patientFirstName,
            @RequestParam(required = false) String patientLastName,
            @RequestParam(required = false) String patientId,
            @RequestParam(required = false) String laborantFirstName,
            @RequestParam(required = false) String laborantLastName,
            @RequestParam(required = false) Date reportDate) {
        if (patientFirstName != null) {
            return reportService.searchReportsByPatientFirstName(patientFirstName);
        }
        if (patientLastName != null) {
            return reportService.searchReportsByPatientLastName(patientLastName);
        }
        if (patientId != null) {
            return reportService.searchReportsByPatientId(patientId);
        }
        if (laborantFirstName != null) {
            return reportService.searchReportsByLaborantFirstName(laborantFirstName);
        }
        if (laborantLastName != null) {
            return reportService.searchReportsByLaborantLastName(laborantLastName);
        }
        if (reportDate != null) {
            return reportService.getReportsByDate(reportDate);
        }
        return reportService.getAllReports();
    }

    @PostMapping
    public Report createReport(@RequestBody Report report) {
        return reportService.saveReport(report);
    }

    @PutMapping("/{id}")
    public Report updateReport(@PathVariable Long id, @RequestBody Report report) {
        report.setId(id);
        return reportService.saveReport(report);
    }

    @DeleteMapping("/{id}")
    public void deleteReport(@PathVariable Long id) {
        reportService.deleteReport(id);
    }


}
