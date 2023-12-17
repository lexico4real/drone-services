package com.oluyinka.droneapi.services;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.oluyinka.droneapi.dto.CreateDispatchHistoryDto;
import com.oluyinka.droneapi.entities.DispatchHistory;
import com.oluyinka.droneapi.entities.Drone;
import com.oluyinka.droneapi.entities.Medication;
import com.oluyinka.droneapi.model.DroneModel;
import com.oluyinka.droneapi.repositories.DispatchHistoryRepository;
import com.oluyinka.droneapi.repositories.DroneRepository;
import com.oluyinka.droneapi.repositories.MedicationRepository;
import com.oluyinka.droneapi.utils.enums.DroneState;

@ExtendWith(MockitoExtension.class)
public class DispatchHistoryServiceTest {

    @Mock
    private DispatchHistoryRepository dispatchHistoryRepository;

    @Mock
    private MedicationRepository medicationRepository;

    @Mock
    private DroneRepository droneRepository;

    @InjectMocks
    private DispatchHistoryService dispatchHistoryService;

    private static final String DRONE_ID = "c918f678-2560-4425-a671-db6094642568";
    private static final String DISPATCH_HISTORY_ID = "ba854e7a-0f5b-42c3-9a40-49b2b2feda81";

    @BeforeEach
    void setUp() {
        Drone drone = new Drone();
        when(droneRepository.findById(DRONE_ID)).thenReturn(Optional.of(drone));
        when(dispatchHistoryRepository.findById(DISPATCH_HISTORY_ID)).thenReturn(Optional.of(new DispatchHistory()));
        List<Medication> expectedMedications = createSampleMedications(
                List.of("5e4be423-19e7-4dcc-baf0-a1ba62d5152b", "1dc7fb87-c917-421f-9c87-8e3255b69426"));
        when(medicationRepository.findAllById(anyList())).thenReturn(expectedMedications);
    }

    @Test
    void testCreateDispatchHistory() {
        // Given
        CreateDispatchHistoryDto createDispatchHistoryDto = new CreateDispatchHistoryDto();
        createDispatchHistoryDto.setId(DISPATCH_HISTORY_ID);
        createDispatchHistoryDto.setDescription("Test Dispatch History");
        Drone drone = new Drone();
        drone.setSerialNumber(DRONE_ID);
        drone.setBatteryCapacity(100);
        drone.setWeightLimit(400.0);
        drone.setState(DroneState.IDLE);
        drone.setModel(DroneModel.Heavyweight);
        createDispatchHistoryDto.setDrone(drone);
        createDispatchHistoryDto.setMedicationIds(
                List.of("5e4be423-19e7-4dcc-baf0-a1ba62d5152b", "1dc7fb87-c917-421f-9c87-8e3255b69426"));

        // When
        DispatchHistory createdDispatchHistory = dispatchHistoryService.createDispatchHistory(createDispatchHistoryDto);

        // Then
        assertNotNull(createdDispatchHistory);
        verify(droneRepository, times(1)).findById(anyString());
        verify(medicationRepository, times(1)).findAllById(anyList());
        verify(dispatchHistoryRepository, times(1)).save(any(DispatchHistory.class));
    }

    @Test
    @Disabled
    void testUpdateDroneState() {

    }

    @Test
    @Disabled
    void testUpdateDroneStateWithDispatchId() {

    }

    private List<Medication> createSampleMedications(List<String> ids) {
        List<Medication> medications = new ArrayList<>();
        for (String id : ids) {
            medications.add(new Medication(id));
        }
        return medications;
    }
}
