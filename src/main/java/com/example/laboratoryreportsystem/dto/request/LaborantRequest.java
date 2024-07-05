package com.example.laboratoryreportsystem.dto.request;

public record LaborantRequest(
        String firstName,
        String lastName,
        String hospitalId
) {
}
