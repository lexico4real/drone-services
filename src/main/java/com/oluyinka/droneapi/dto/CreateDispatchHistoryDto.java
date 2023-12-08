package com.oluyinka.droneapi.dto;

import java.util.ArrayList;
import java.util.List;

import com.oluyinka.droneapi.entities.Drone;

import lombok.Data;

@Data
public class CreateDispatchHistoryDto {

    private String description;

    private Drone drone;

    private List<CreateMedicationDto> medications = new ArrayList<>();

    private List<String> medicationIds = new ArrayList<>();
}

