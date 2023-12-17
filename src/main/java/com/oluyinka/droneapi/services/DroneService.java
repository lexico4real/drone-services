package com.oluyinka.droneapi.services;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.crossstore.ChangeSetPersister.NotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.oluyinka.droneapi.dto.*;
import com.oluyinka.droneapi.entities.Drone;
import com.oluyinka.droneapi.repositories.DroneRepository;

import java.util.List;

@Service
public class DroneService {

    private final Logger logger;

    @Autowired
    private final DroneRepository droneRepository;

    public DroneService(DroneRepository droneRepository, Logger logger) {
        this.droneRepository = droneRepository;
        this.logger = logger;
    }

    public Drone createDrone(CreateDroneDto createDroneDto) {
        return droneRepository.createDrone(createDroneDto);
    }

    public List<Drone> getAllDrones() {
        return droneRepository.getAllDrones();
    }

    @Scheduled(cron = "*/10 * * * * *")
    public void getDroneBatteryStatus() {
        List<Drone> drones = getAllDrones();
        processDroneBatteryStatus(drones);
    }

    public void processDroneBatteryStatus(List<Drone> drones) {
        for (Drone drone : drones) {
            int batteryLevel = drone.getBatteryCapacity();
            logger.info("Serial Number: " + drone.getSerialNumber() + ", Battery Level: " + batteryLevel + "%");
        }
    }

    public Drone getDroneById(String id) throws NotFoundException {
        return droneRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                        "Drone not found with serial number: " + id));
    }

    public Drone updateDrone(String id, UpdateDroneDto updateDroneDto) throws NotFoundException {
        Drone drone = getDroneById(id);
        drone.setModel(updateDroneDto.getModel());
        drone.setBatteryCapacity(updateDroneDto.getBatteryCapacity());
        drone.setState(updateDroneDto.getState());
        drone.setWeightLimit(updateDroneDto.getWeightLimit());
        droneRepository.save(drone);
        return drone;
    }

    public void deleteDroneById(String id) throws NotFoundException {
        Drone found = getDroneById(id);
        droneRepository.deleteById(found.getSerialNumber());
    }
}
