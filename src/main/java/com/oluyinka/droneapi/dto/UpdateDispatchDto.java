package com.oluyinka.droneapi.dto;

import com.oluyinka.droneapi.entities.Drone;

import lombok.Data;

import java.util.List;

@Data
public class UpdateDispatchDto {

    private String description;

    private Drone drone;

    // private List<CreateMedicationDto> medications;

    private List<String> medicationIds;
}

