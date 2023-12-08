package com.oluyinka.droneapi.dto;


import lombok.Data;

import java.util.ArrayList;
import java.util.List;

import com.oluyinka.droneapi.entities.Drone;

@Data
public class CreateDispatchDto {

    private String id;

    private String description;

    private Drone drone;

    private String droneId;

    private List<CreateMedicationDto> medications = new ArrayList<>();

    private List<String> medicationIds = new ArrayList<>();
}

