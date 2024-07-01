package com.example.laboratoryreportsystem.service;

import com.example.laboratoryreportsystem.entity.Report;
import com.example.laboratoryreportsystem.repository.ReportRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class ReportService {
    @Autowired
    private ReportRepository reportRepository;

    public List<Report> getAllReports() {
        return reportRepository.findAll();
    }

    public Report getReportById(Long id) {
        return reportRepository.findById(id).orElse(null);
    }

    public List<Report> searchReportsByPatientFirstName(String firstName) {
        return reportRepository.findByPatientFirstNameContaining(firstName);
    }

    public List<Report> searchReportsByPatientLastName(String lastName) {
        return reportRepository.findByPatientLastNameContaining(lastName);
    }

    public List<Report> searchReportsByPatientId(String patientId) {
        return reportRepository.findByPatientPatientId(patientId);
    }

    public List<Report> searchReportsByLaborantFirstName(String firstName) {
        return reportRepository.findByLaborantFirstNameContaining(firstName);
    }

    public List<Report> searchReportsByLaborantLastName(String lastName) {
        return reportRepository.findByLaborantLastNameContaining(lastName);
    }

    public List<Report> getReportsByDate(Date reportDate) {
        return reportRepository.findByReportDateOrderByReportDate(reportDate);
    }

    public Report saveReport(Report report) {
        return reportRepository.save(report);
    }

    public void deleteReport(Long id) {
        reportRepository.deleteById(id);
    }



}
