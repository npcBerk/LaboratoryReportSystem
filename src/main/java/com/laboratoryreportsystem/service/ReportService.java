package com.laboratoryreportsystem.service;

import com.laboratoryreportsystem.entity.Report;
import com.laboratoryreportsystem.repository.LaborantRepository;
import com.laboratoryreportsystem.repository.PatientRepository;
import com.laboratoryreportsystem.repository.ReportRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class ReportService {
    @Autowired
    private ReportRepository reportRepository;
    @Autowired
    private PatientRepository patientRepository;
    @Autowired
    private LaborantRepository laborantRepository;

    public List<Report> getAllReports() {
        return reportRepository.findAll();
    }


    public Report getReportById(Long id) {
        return reportRepository.findById(id).orElseThrow(EntityNotFoundException::new);
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

    @Transactional
    public Report saveReport(Report report) {
        if (reportRepository.existsByFileNumber(report.getFileNumber())) {
            throw new IllegalArgumentException("Report with file number " + report.getFileNumber() + " already exists.");
        }

        if (!patientRepository.existsById(report.getPatient().getId())) {
            throw new IllegalArgumentException("Patient with ID " + report.getPatient().getId() + " does not exist.");
        }

        if (!laborantRepository.existsByHospitalId(report.getLaborant().getHospitalId())) {
            throw new IllegalArgumentException("Laborant with hospital ID " + report.getLaborant().getHospitalId() + " does not exist.");
        }

        return reportRepository.save(report);
    }

    public List<Report> getAllReportsSortedByDate(Sort.Direction direction) {
        return reportRepository.findAll(Sort.by(direction, "reportDate"));
    }

    @Transactional
    public void updateReport(Long id, String fileNumber, String patientId, String diagnosisTitle, String diagnosisDetails, Date reportDate, String laborantHospitalId) {
        Report report = reportRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Report with ID " + id + " not found."));

        if (fileNumber != null && !fileNumber.isEmpty() && !fileNumber.equals(report.getFileNumber())) {
            if (reportRepository.existsByFileNumber(fileNumber)) {
                throw new IllegalArgumentException("Report with file number " + fileNumber + " already exists.");
            }
            report.setFileNumber(fileNumber);
        }
        if (patientId != null && !patientId.isEmpty() && !patientId.equals(report.getPatient().getPatientId())) {
            if(!patientRepository.existsByPatientId(patientId)) {
               throw new IllegalArgumentException("Patient with ID " + patientId + " does not exist.");
            }
            report.setPatient(patientRepository.findByPatientId(patientId));
        }
        if (diagnosisTitle != null && !diagnosisTitle.isEmpty() && !diagnosisTitle.equals(report.getDiagnosisTitle())) {
            report.setDiagnosisTitle(diagnosisTitle);
        }
        if (diagnosisDetails != null && !diagnosisDetails.isEmpty() && !diagnosisDetails.equals(report.getDiagnosisDetails())) {
            report.setDiagnosisDetails(diagnosisDetails);
        }
        if (reportDate != null && !reportDate.equals(report.getReportDate())) {
            report.setReportDate(reportDate);
        }
        if (laborantHospitalId != null && !laborantHospitalId.isEmpty() && !laborantHospitalId.equals(report.getLaborant().getHospitalId())) {
            if (!laborantRepository.existsByHospitalId(laborantHospitalId)) {
                throw new IllegalArgumentException("Laborant with hospital ID " + laborantHospitalId + " not found.");
            }
            report.setLaborant(laborantRepository.findByHospitalId(laborantHospitalId));
        }

        reportRepository.save(report);
    }

    /*public void updateReport(Long id, String fileNumber, String patientId, String diagnosisTitle, String diagnosisDetails, Date reportDate, String laborantHospitalId) {
        Report report = reportRepository.findById(id).orElseThrow(() -> new IllegalStateException("Report not found"));

        if (fileNumber != null && !fileNumber.isEmpty() && !fileNumber.equals(report.getFileNumber())) {
            report.setFileNumber(fileNumber);
        }
        if (patientId != null && !patientId.isEmpty() && !patientId.equals(report.getPatient().getPatientId())) {
            report.getPatient().setPatientId(patientId);
        }
        if (diagnosisTitle != null && !diagnosisTitle.isEmpty() && !diagnosisTitle.equals(report.getDiagnosisTitle())) {
            report.setDiagnosisTitle(diagnosisTitle);
        }
        if (diagnosisDetails != null && !diagnosisDetails.isEmpty() && !diagnosisDetails.equals(report.getDiagnosisDetails())) {
            report.setDiagnosisDetails(diagnosisDetails);
        }
        if (reportDate != null && !reportDate.equals(report.getReportDate())) {
            report.setReportDate(reportDate);
        }
        if (laborantHospitalId != null && !laborantHospitalId.isEmpty() && !laborantHospitalId.equals(report.getLaborant().getHospitalId())) {
            report.getLaborant().setHospitalId(laborantHospitalId);
        }

        reportRepository.save(report);
    }*/

    @Transactional
    public void deleteReport(Long id) {
        if (!reportRepository.existsById(id)) {
            throw new IllegalArgumentException("Report with ID " + id + " does not exist.");
        }
        reportRepository.deleteById(id);
    }

}
