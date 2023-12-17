package com.oluyinka.droneapi.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.slf4j.Logger;
import org.springframework.data.crossstore.ChangeSetPersister.NotFoundException;
import org.springframework.scheduling.annotation.SchedulingConfigurer;
import org.springframework.scheduling.config.ScheduledTaskRegistrar;

import com.oluyinka.droneapi.dto.UpdateDroneDto;
import com.oluyinka.droneapi.entities.Drone;
import com.oluyinka.droneapi.model.DroneModel;
import com.oluyinka.droneapi.repositories.DroneRepository;

public class DroneServiceTest implements SchedulingConfigurer {

    private Logger LOGGER = mock(Logger.class);

    @Mock
    private DroneRepository droneRepository;

    @Mock
    private DroneService droneService;

    @BeforeEach
    void setUp() {
        droneRepository = mock(DroneRepository.class);
        LOGGER = mock(Logger.class);
        droneService = new DroneService(droneRepository, LOGGER);
    }

    @Test
    void testDeleteDroneById() throws NotFoundException {
        String droneId = "67f0d078-4454-45bf-adea-01d7a32cc894";
        Drone existingDrone = new Drone(droneId);
        when(droneRepository.findById(droneId)).thenReturn(Optional.of(existingDrone));

        droneService.deleteDroneById(droneId);

        verify(droneRepository, times(1)).deleteById(droneId);
    }

    @Test
    void testGetDroneBatteryStatus() {
        Logger logger = mock(Logger.class);

        DroneService droneService = new DroneService(droneRepository, logger);

        List<Drone> droneList = Arrays.asList(
                new Drone(DroneModel.Heavyweight, 90, 350.7),
                new Drone(DroneModel.Middleweight, 70, 300.0));

        droneService.processDroneBatteryStatus(droneList);

        verify(logger, times(2)).info(anyString());
    }

    @Test
    void testGetDroneById() throws NotFoundException {
        // Given
        String droneId = "f318598e-fb0c-4b8b-a6d6-b7afc6d8fa31";
        Drone expectedDrone = new Drone();
        when(droneRepository.findById(droneId)).thenReturn(Optional.of(expectedDrone));

        // When
        Drone actualDrone = droneService.getDroneById(droneId);

        // Then
        assertEquals(expectedDrone, actualDrone);
    }

    @Test
    void testUpdateDrone() throws NotFoundException {
        // Given
        String droneId = "a7f7018d-cbff-473a-8d73-e673d109c884";
        UpdateDroneDto updateDroneDto = new UpdateDroneDto();
        updateDroneDto.setModel(DroneModel.Cruiserweight);
        updateDroneDto.setBatteryCapacity(90);
        updateDroneDto.setWeightLimit(340.0);
        Drone existingDrone = new Drone();
        when(droneRepository.findById(droneId)).thenReturn(Optional.of(existingDrone));

        // When
        Drone updatedDrone = droneService.updateDrone(droneId, updateDroneDto);

        // Then
        assertEquals(updateDroneDto.getModel(), updatedDrone.getModel());
        assertEquals(updateDroneDto.getBatteryCapacity(), updatedDrone.getBatteryCapacity());
        assertEquals(updateDroneDto.getWeightLimit(), updatedDrone.getWeightLimit());
        Mockito.verify(droneRepository, Mockito.times(1)).save(existingDrone);
    }

    @Override
    public void configureTasks(ScheduledTaskRegistrar taskRegistrar) {
        //
    }
}
