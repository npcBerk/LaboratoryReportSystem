package com.laboratoryreportsystem.dto.mapper;

import com.laboratoryreportsystem.dto.request.LaborantRequest;
import com.laboratoryreportsystem.dto.response.LaborantResponse;
import com.laboratoryreportsystem.entity.Laborant;
import com.laboratoryreportsystem.entity.Report;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class LaborantDTOMapper {

    public LaborantResponse laborantToResponse(Laborant laborant) {
        return new LaborantResponse(
                laborant.getId(),
                laborant.getFirstName(),
                laborant.getLastName(),
                laborant.getHospitalId(),
                laborant.getReports()
                        .stream()
                        .map(Report::getFileNumber)
                        .toList());
    }

    public List<LaborantResponse> laborantListToResponse(List<Laborant> laborants) {
        return laborants.stream()
                .map(this::laborantToResponse)
                .toList();
    }

    public Laborant requestToLaborant(LaborantRequest request) {
        return Laborant.builder()
                .firstName(request.firstName())
                .lastName(request.lastName())
                .hospitalId(request.hospitalId())
                .build();
    }


}
