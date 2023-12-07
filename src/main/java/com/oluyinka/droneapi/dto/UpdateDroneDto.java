package com.oluyinka.droneapi.dto;

import com.oluyinka.droneapi.model.DroneModel;
import com.oluyinka.droneapi.utils.enums.DroneState;

import jakarta.validation.Valid;
import lombok.Data;

@Data
public class UpdateDroneDto {

    @Valid
    
    private DroneModel model;

    private Integer batteryCapacity;

    private Double weightLimit;

    private DroneState state;
}
