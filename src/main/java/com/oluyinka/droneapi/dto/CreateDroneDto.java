package com.oluyinka.droneapi.dto;

import com.oluyinka.droneapi.model.DroneModel;

import jakarta.validation.Valid;
import lombok.Data;

@Data
public class CreateDroneDto {

    @Valid
    
    private DroneModel model;

    private Integer batteryCapacity;

    private Double weightLimit;
}

