package com.oluyinka.droneapi.dto;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import com.oluyinka.droneapi.model.DroneModel;
import com.oluyinka.droneapi.utils.enums.DroneState;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UpdateDroneDto {

    @NotNull(message = "Model is required")
    private DroneModel model;

    @Min(value = 0, message = "Battery capacity cannot be less than 0")
    @Max(value = 100, message = "Battery capacity cannot be more than 100")
    private Integer batteryCapacity;

    @Min(value = 0, message = "Weight limit cannot be less than 0")
    @Max(value = 500, message = "Weight limit cannot be more than 500")
    private Double weightLimit;

    @NotNull(message = "State is required")
    private DroneState state;

    public Integer getBatteryCapacity() {
        return batteryCapacity != null ? batteryCapacity : 0;
    }

    public UpdateDroneDto(int batteryCapacity, double weightLimit, DroneState state) {
        this.batteryCapacity = batteryCapacity;
        this.weightLimit = weightLimit;
        this.state = state;
    }
}
