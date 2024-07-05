package com.example.laboratoryreportsystem.repository;

import com.example.laboratoryreportsystem.entity.Report;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Date;
import java.util.List;

public interface ReportRepository extends JpaRepository<Report, Long> {
    List<Report> findByPatientFirstNameContaining(String firstName);
    List<Report> findByPatientLastNameContaining(String lastName);
    List<Report> findByPatientPatientId(String patientId);
    List<Report> findByLaborantFirstNameContaining(String firstName);
    List<Report> findByLaborantLastNameContaining(String lastName);
    List<Report> findByReportDateOrderByReportDate(Date reportDate);
    boolean existsByFileNumber(String fileNumber);
}
