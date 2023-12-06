package com.oluyinka.droneapi.dto;

import com.oluyinka.droneapi.model.DroneModel;

import lombok.Data;

import javax.validation.constraints.*;

@Data
public class CreateDroneDto {

    @NotNull(message = "Model is required")
    private DroneModel model;

    @NotNull(message = "Battery capacity is required")
    @Min(value = 0, message = "Battery capacity cannot be less than 0")
    @Max(value = 100, message = "Battery capacity cannot be more than 100")
    private Integer batteryCapacity;

    @NotNull(message = "Weight limit is required")
    @Min(value = 0, message = "Weight limit cannot be less than 0")
    @Max(value = 500, message = "Weight limit cannot be more than 500")
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

