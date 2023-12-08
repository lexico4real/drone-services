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

    public static ResponseEntity<?> validateMedicationName(String name) {
        String namePattern = "^[a-zA-Z0-9-_]+$";

        if (!name.matches(namePattern)) {
            String errorMessage = "Name can only contain letters, numbers, '-', and '_'";
            return errorResponse(HttpStatus.BAD_REQUEST, errorMessage);
        }

        return null;
    }

    public static ResponseEntity<?> validateMedicationCode(String code) {
        String codePattern = "^[A-Z0-9_]+$";

        if (!code.matches(codePattern)) {
            String errorMessage = "Code can only contain uppercase letters, numbers, and underscores";
            return errorResponse(HttpStatus.BAD_REQUEST, errorMessage);
        }

        return null;
    }
}
