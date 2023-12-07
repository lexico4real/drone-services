package com.oluyinka.droneapi.dto;

import com.oluyinka.droneapi.model.DroneModel;

public class CreateDroneDto {

    private DroneModel model;

    private Integer batteryCapacity;

    private Double weightLimit;

    public CreateDroneDto() {
    }

    public CreateDroneDto(DroneModel model, Integer batteryCapacity, Double weightLimit) {
        this.model = model;
        this.batteryCapacity = batteryCapacity;
        this.weightLimit = weightLimit;
    }

    public DroneModel getModel() {
        return model;
    }

    public void setModel(DroneModel model) {
        this.model = model;
    }

    public Integer getBatteryCapacity() {
        return batteryCapacity;
    }

    public void setBatteryCapacity(Integer batteryCapacity) {
        this.batteryCapacity = batteryCapacity;
    }

    public Double getWeightLimit() {
        return weightLimit;
    }

    public void setWeightLimit(Double weightLimit) {
        this.weightLimit = weightLimit;
    }
}

