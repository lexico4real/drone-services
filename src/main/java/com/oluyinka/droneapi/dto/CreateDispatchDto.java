package com.oluyinka.droneapi.dto;


import lombok.Data;

import java.util.List;

import com.oluyinka.droneapi.entities.Drone;

@Data
public class CreateDispatchDto {

    private String description;

    private Drone drone;

    // private List<CreateMedicationDto> medications;

    private List<String> medicationIds;
}

