package com.oluyinka.droneapi.dto;

import com.oluyinka.droneapi.model.DroneModel;

import lombok.Data;

@Data
public class CreateDroneDto {

    private DroneModel model;

    private Integer batteryCapacity;

    private Double weightLimit;
}

