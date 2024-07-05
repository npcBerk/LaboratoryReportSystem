package com.laboratoryreportsystem.dto.response;

import java.util.List;

public record LaborantResponse(
        Long id,
        String firstName,
        String lastName,
        String hospitalId,
        List<String> reportFileNumber
){

}
