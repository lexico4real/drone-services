package com.oluyinka.droneapi.services;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.oluyinka.droneapi.dto.CreateDispatchDto;
import com.oluyinka.droneapi.entities.Dispatch;
import com.oluyinka.droneapi.entities.Drone;
import com.oluyinka.droneapi.entities.Medication;
import com.oluyinka.droneapi.repositories.DispatchRepository;
import com.oluyinka.droneapi.repositories.DroneRepository;
import com.oluyinka.droneapi.repositories.MedicationRepository;
import com.oluyinka.droneapi.utils.enums.DroneState;

@ExtendWith(MockitoExtension.class)
public class DispatchServiceTest {

    @Mock
    private DispatchRepository dispatchRepository;

    @Mock
    private MedicationRepository medicationRepository;

    @Mock
    private DroneRepository droneRepository;

    @InjectMocks
    private DispatchService dispatchService;

    @Test
    void testCreateDispatch() {
        // Given
        String id = "c918f678-2560-4425-a671-db6094642568";
        CreateDispatchDto createDispatchDto = new CreateDispatchDto();
        createDispatchDto.setDroneId(id);
        createDispatchDto.setDescription("Test Dispatch");

        List<String> medicationIds = Arrays.asList("81df5a8e-8839-48e3-a346-fe36ac7fb440",
                "7fd81637-135a-41e3-b777-5907b29aca39");
        createDispatchDto.setMedicationIds(medicationIds);

        Medication medication1 = new Medication("81df5a8e-8839-48e3-a346-fe36ac7fb440");
        Medication medication2 = new Medication("7fd81637-135a-41e3-b777-5907b29aca39");

        Drone drone = new Drone();
        drone.setSerialNumber(id);
        drone.setWeightLimit(320.0);
        drone.setBatteryCapacity(80);
        drone.setState(DroneState.IDLE);

        when(droneRepository.findById(id)).thenReturn(Optional.of(drone));
        when(medicationRepository.findAllById(medicationIds)).thenReturn(Arrays.asList(medication1, medication2));
        when(dispatchRepository.existsByDroneSerialNumber(id)).thenReturn(false);
        when(dispatchRepository.save(any(Dispatch.class))).thenReturn(new Dispatch());

        // When
        Dispatch createdDispatch = dispatchService.createDispatch(createDispatchDto);

        // Then
        assertNotNull(createdDispatch);
        verify(droneRepository, times(2)).findById(eq(id));
        verify(medicationRepository, times(1)).findAllById(eq(medicationIds));
        verify(dispatchRepository, times(1)).existsByDroneSerialNumber(eq(id));
        verify(dispatchRepository, times(1)).save(any(Dispatch.class));

        // verifyNoMoreInteractions(droneRepository);
    }

    @Test
    void testDeleteDispatchById() {

    }

    @Test
    void testFindByDroneId() {

    }

    @Test
    void testFindByMedicationId() {

    }

    @Test
    void testGetAllDispatches() {

    }

    @Test
    void testGetDispatchById() {

    }

    @Test
    void testGetDroneById() {

    }

    @Test
    void testGetMedicationByIds() {

    }

    @Test
    void testResetDroneState() {

    }

    @Test
    void testUpdateDispatch() {

    }

    @Test
    void testUpdateDroneState() {

    }

    @Test
    void testUpdateDroneStateWithDispatchId() {

    }
}
