package com.laboratoryreportsystem.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "patient")
public class Patient {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String firstName;
    private String lastName;
    private String patientId; // TC Kimlik Numarası

    public Patient(String firstName, String lastName, String patientId) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.patientId = patientId;
    }

    @OneToMany(mappedBy = "patient")
    private List<Report> reports;

}
