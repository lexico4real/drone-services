package com.oluyinka.droneapi.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.oluyinka.droneapi.dto.CreateDroneDto;
import com.oluyinka.droneapi.entities.Drone;
import com.oluyinka.droneapi.utils.enums.DroneState;

import java.util.List;

@Repository
public interface DroneRepository extends JpaRepository<Drone, String> {

    default Drone createDrone(CreateDroneDto createDroneDto) {
        try {
            Drone drone = new Drone();
            drone.setModel(createDroneDto.getModel());
            drone.setState(DroneState.IDLE);
            drone.setBatteryCapacity(createDroneDto.getBatteryCapacity());
            drone.setWeightLimit(createDroneDto.getWeightLimit());
            return save(drone);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            throw new RuntimeException("Error creating drone", e);
        }
    }

    default List<Drone> getAllDrones() {
        List<Drone> drones = this.findAll();
        return drones;
    }
}
