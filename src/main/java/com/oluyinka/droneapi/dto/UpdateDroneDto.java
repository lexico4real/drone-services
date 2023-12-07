package com.oluyinka.droneapi.dto;

import com.oluyinka.droneapi.model.DroneModel;
import com.oluyinka.droneapi.utils.enums.DroneState;

import lombok.Data;

@Data
public class UpdateDroneDto {
    
    private DroneModel model;

    private Integer batteryCapacity;

    private Double weightLimit;

    private DroneState state;
}
