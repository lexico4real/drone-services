package com.oluyinka.droneapi.repositories;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import com.oluyinka.droneapi.dto.CreateDroneDto;
import com.oluyinka.droneapi.entities.Drone;
import com.oluyinka.droneapi.model.DroneModel;

@DataJpaTest
public class DroneRepositoryTest {

    @Mock
    private DroneRepository droneRepository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testCreateDrone() {
        // Given
        CreateDroneDto createDroneDto = new CreateDroneDto();
        createDroneDto.setModel(DroneModel.Heavyweight);
        createDroneDto.setBatteryCapacity(100);
        createDroneDto.setWeightLimit(400.0);

        Drone expectedDrone = new Drone();
        expectedDrone.setModel(createDroneDto.getModel());
        expectedDrone.setBatteryCapacity(createDroneDto.getBatteryCapacity());
        expectedDrone.setWeightLimit(createDroneDto.getWeightLimit());

        when(droneRepository.createDrone(createDroneDto)).thenReturn(expectedDrone);

        // When
        Drone createdDrone = droneRepository.createDrone(createDroneDto);

        // Then
        assertNotNull(createdDrone);
        assertEquals(expectedDrone, createdDrone);
        Mockito.verify(droneRepository, Mockito.times(1)).createDrone(createDroneDto);
    }

    @Test
    void testGetAllDrones() {
        // Given
        Drone drone1 = new Drone();
        Drone drone2 = new Drone();
        List<Drone> expectedDrones = Arrays.asList(drone1, drone2);

        when(droneRepository.getAllDrones()).thenReturn(expectedDrones);

        // When
        List<Drone> actualDrones = droneRepository.getAllDrones();

        // Then
        assertEquals(expectedDrones, actualDrones);
        Mockito.verify(droneRepository, Mockito.times(1)).getAllDrones();
    }
}
