package com.oluyinka.droneapi.repositories;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import com.oluyinka.droneapi.entities.Dispatch;
import com.oluyinka.droneapi.services.DispatchService;

@DataJpaTest
public class DispatchRepositoryTest {

    @Mock
    private DispatchRepository dispatchRepository;

    @InjectMocks
    private DispatchService dispatchService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testExistsByDroneSerialNumber() {
        // Given
        String droneSerialNumber = "2e9254eb-3f0b-432e-9b00-390e5d25aa6b";
        when(dispatchRepository.existsByDroneSerialNumber(droneSerialNumber)).thenReturn(true);

        // When
        boolean result = dispatchRepository.existsByDroneSerialNumber(droneSerialNumber);

        // Then
        assertTrue(result);
        verify(dispatchRepository, times(1)).existsByDroneSerialNumber(droneSerialNumber);
    }

    @Test
    void testFindAllWithDrone() {
        // Given
        Dispatch dispatch = new Dispatch();
        when(dispatchRepository.findAllWithDrone()).thenReturn(Collections.singletonList(dispatch));

        // When
        List<Dispatch> result = dispatchRepository.findAllWithDrone();

        // Then
        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(dispatch, result.get(0));
        verify(dispatchRepository, times(1)).findAllWithDrone();
    }

    @Test
    void testFindByDroneId() {
        // Given
        String droneId = "6779acf4-279b-4062-91b4-62b2ecaa12cc";
        Dispatch dispatch = new Dispatch();
        when(dispatchRepository.findByDroneId(droneId)).thenReturn(Collections.singletonList(dispatch));

        // When
        List<Dispatch> result = dispatchRepository.findByDroneId(droneId);

        // Then
        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(dispatch, result.get(0));
        verify(dispatchRepository, times(1)).findByDroneId(droneId);
    }

    @Test
    void testFindByMedicationId() {
        // Given
        String medicationId = "54202d24-9569-4008-95eb-3e522bf9453b";
        Dispatch dispatch = new Dispatch();
        when(dispatchRepository.findByMedicationId(medicationId)).thenReturn(Collections.singletonList(dispatch));

        // When
        List<Dispatch> result = dispatchRepository.findByMedicationId(medicationId);

        // Then
        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(dispatch, result.get(0));
        verify(dispatchRepository, times(1)).findByMedicationId(medicationId);
    }
}
