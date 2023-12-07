package com.oluyinka.droneapi.dto;

import lombok.Data;

@Data
public class CreateMedicationDto {
    
    private String name;

    private Double weight;

    private String code;

    private String image;
}
