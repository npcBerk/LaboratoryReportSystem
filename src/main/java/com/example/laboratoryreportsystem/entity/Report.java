package com.example.laboratoryreportsystem.entity;

import jakarta.persistence.*;
import java.util.Date;

@Entity
public class Report {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String fileNumber;
    private String diagnosisTitle;

    @Column(columnDefinition = "TEXT")//Tanı detaylarını database'de TEXT olarak tutmak için
    private String diagnosisDetails;
    private Date reportDate;

    @OneToOne
    @JoinColumn(name = "image_id")
    private ImageData reportImage;
    @ManyToOne
    @JoinColumn(name = "laborant_id")
    private Laborant laborant;

    @ManyToOne
    @JoinColumn(name = "patient_id")
    private Patient patient;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFileNumber() {
        return fileNumber;
    }

    public void setFileNumber(String fileNumber) {
        this.fileNumber = fileNumber;
    }

    public String getDiagnosisTitle() {
        return diagnosisTitle;
    }

    public void setDiagnosisTitle(String diagnosisTitle) {
        this.diagnosisTitle = diagnosisTitle;
    }

    public String getDiagnosisDetails() {
        return diagnosisDetails;
    }

    public void setDiagnosisDetails(String diagnosisDetails) {
        this.diagnosisDetails = diagnosisDetails;
    }

    public Date getReportDate() {
        return reportDate;
    }

    public void setReportDate(Date reportDate) {
        this.reportDate = reportDate;
    }

    public ImageData getReportImage() {
        return reportImage;
    }

    public void setReportImage(ImageData reportImage) {
        this.reportImage = reportImage;
    }

    public Laborant getLaborant() {
        return laborant;
    }

    public void setLaborant(Laborant laborant) {
        this.laborant = laborant;
    }

    public Patient getPatient() {
        return patient;
    }

    public void setPatient(Patient patient) {
        this.patient = patient;
    }
}
