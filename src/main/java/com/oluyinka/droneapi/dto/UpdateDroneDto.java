package com.oluyinka.droneapi.dto;

import com.oluyinka.droneapi.model.DroneModel;
import com.oluyinka.droneapi.utils.enums.DroneState;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UpdateDroneDto {

    private DroneModel model;

    private Integer batteryCapacity;

    private Double weightLimit;

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
