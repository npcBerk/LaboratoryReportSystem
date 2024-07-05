package com.laboratoryreportsystem.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.Date;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
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


    @Lob
    private String imageBase64;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "laborant_id")
    private Laborant laborant;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "patient_id")
    private Patient patient;

}
