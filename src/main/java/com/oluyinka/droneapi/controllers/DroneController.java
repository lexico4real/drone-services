package com.oluyinka.droneapi.controllers;

import java.util.List;

import jakarta.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.crossstore.ChangeSetPersister.NotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.oluyinka.droneapi.dto.*;
import com.oluyinka.droneapi.entities.Drone;
import com.oluyinka.droneapi.services.DroneService;
import com.oluyinka.droneapi.utils.ControllerHelper;

@RestController
@RequestMapping("/api/drones")
public class DroneController {

    @Autowired
    private final DroneService droneService;

    public DroneController(DroneService droneService) {
        this.droneService = droneService;
    }

    @PostMapping()
    public ResponseEntity<?> createDrone(@Valid @RequestBody CreateDroneDto createDroneDto) {
        if (!ControllerHelper.isValidDrone(createDroneDto)) {
            return ControllerHelper.errorResponse(HttpStatus.BAD_REQUEST, "Invalid drone parameters");
        }

        try {
            Drone createdDrone = droneService.createDrone(createDroneDto);
            return ResponseEntity.status(HttpStatus.CREATED).body(createdDrone);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping
    public ResponseEntity<List<Drone>> getAllDrones() {
        List<Drone> drones = droneService.getAllDrones();
        return new ResponseEntity<>(drones, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Drone> getDroneById(@PathVariable String id) throws NotFoundException {
        Drone drone = droneService.getDroneById(id);
        return ResponseEntity.ok(drone);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateDrone(@PathVariable String id, @Valid @RequestBody UpdateDroneDto updateDroneDto)
            throws NotFoundException {
        if (!ControllerHelper.isValidDroneUpdate(updateDroneDto)) {
            return ControllerHelper.errorResponse(HttpStatus.BAD_REQUEST, "Invalid drone update parameters");
        }
        Drone updatedDrone = droneService.updateDrone(id, updateDroneDto);
        return ResponseEntity.ok(updatedDrone);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteDroneById(@PathVariable String id) throws NotFoundException {
        droneService.deleteDroneById(id);
        return ResponseEntity.noContent().build();
    }
}
