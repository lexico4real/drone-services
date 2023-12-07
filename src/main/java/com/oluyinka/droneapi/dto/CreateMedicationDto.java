package com.oluyinka.droneapi.dto;


import jakarta.validation.Valid;
import lombok.Data;

@Data
public class CreateMedicationDto {

    @Valid
    
    private String name;

    private Double weight;

    private String code;

    private String image;
}
