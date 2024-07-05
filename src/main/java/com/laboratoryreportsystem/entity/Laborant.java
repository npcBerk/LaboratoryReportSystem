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
public class Laborant {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String firstName;
    private String lastName;
    private String hospitalId; // 7 Haneli

    public Laborant(String firstName, String lastName, String hospitalId) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.hospitalId = hospitalId;
    }

    @OneToMany(mappedBy = "laborant")
    private List<Report> reports;

}
