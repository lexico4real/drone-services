package com.oluyinka.droneapi.utils;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.oluyinka.droneapi.dto.CreateDroneDto;
import com.oluyinka.droneapi.dto.UpdateDroneDto;
import com.oluyinka.droneapi.utils.exception.ErrorResponse;

public class ControllerHelper {

    public static boolean isValidDrone(CreateDroneDto createDroneDto) {
        return isBetweenI(createDroneDto.getBatteryCapacity(), 0, 100) &&
                isBetweenD(createDroneDto.getWeightLimit(), 0, 500);
    }

    public static boolean isValidDroneUpdate(UpdateDroneDto updateDroneDto) {
        if (updateDroneDto.getBatteryCapacity() == null) {
            return false;
        }
        return isBetweenI(updateDroneDto.getBatteryCapacity(), 0, 100) &&
                isBetweenD(updateDroneDto.getWeightLimit(), 0, 500);
    }

    public static boolean isBetweenI(Integer value, int min, int max) {
        return value != null && value >= min && value <= max;
    }

    public static boolean isBetweenD(Double value, double min, double max) {
        return value != null && value >= min && value <= max;
    }

    public static ResponseEntity<?> errorResponse(HttpStatus status, String errorMessage) {
        return ResponseEntity.status(status)
                .body(new ErrorResponse(status.value(), errorMessage));
    }
}
