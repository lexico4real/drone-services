package com.oluyinka.droneapi.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.oluyinka.droneapi.dto.CreateDroneDto;
import com.oluyinka.droneapi.entities.DroneEntity;
import com.oluyinka.droneapi.utils.enums.DroneState;

import java.util.List;


@Repository
public interface DroneRepository extends JpaRepository<DroneEntity, String> {

    default DroneEntity createDrone(CreateDroneDto createDroneDto) {
        try {
            DroneEntity drone = new DroneEntity();
            drone.setModel(createDroneDto.getModel());
            drone.setState(DroneState.IDLE);
            drone.setBatteryCapacity(createDroneDto.getBatteryCapacity());
            drone.setWeightLimit(createDroneDto.getWeightLimit());
            return save(drone);
        } catch (Exception e) {
            throw new RuntimeException("Error creating drone", e);
        }
    }

    default List<DroneEntity> getAllDrones() {
        List<DroneEntity> drones = this.findAll();
        return drones;
    }
}
