package com.oluyinka.droneapi.repositories;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.crossstore.ChangeSetPersister.NotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import com.oluyinka.droneapi.dto.CreateMedicationDto;
import com.oluyinka.droneapi.entities.Medication;

@DataJpaTest
public class MedicationRepositoryTest {

    @Mock
    private MedicationRepository medicationRepository;

    @Test
    void testCreateMedication() {
        // Given
        CreateMedicationDto createMedicationDto = new CreateMedicationDto();
        createMedicationDto.setName("Med1");
        createMedicationDto.setWeight(5.0);
        createMedicationDto.setCode("ABC123");
        createMedicationDto.setImage("image-url");

        Medication expectedMedication = new Medication();
        expectedMedication.setName(createMedicationDto.getName());
        expectedMedication.setWeight(createMedicationDto.getWeight());
        expectedMedication.setCode(createMedicationDto.getCode());
        expectedMedication.setImage(createMedicationDto.getImage());

        when(medicationRepository.createMedication(createMedicationDto)).thenReturn(expectedMedication);

        // When
        Medication createdMedication = medicationRepository.createMedication(createMedicationDto);

        // Then
        assertNotNull(createdMedication);
        assertEquals(expectedMedication, createdMedication);
        Mockito.verify(medicationRepository, Mockito.times(1)).createMedication(createMedicationDto);
    }

    @Test
    void testGetAllMedications() {
        // Given
        Medication medication1 = new Medication("81df5a8e-8839-48e3-a346-fe36ac7fb440");
        Medication medication2 = new Medication("7fd81637-135a-41e3-b777-5907b29aca39");
        List<Medication> expectedMedications = Arrays.asList(medication1, medication2);

        when(medicationRepository.getAllMedications()).thenReturn(expectedMedications);

        // When
        List<Medication> actualMedications = medicationRepository.getAllMedications();

        // Then
        assertNotNull(actualMedications);
        assertEquals(expectedMedications.size(), actualMedications.size());
        assertTrue(actualMedications.containsAll(expectedMedications));
    }

    @Test
    void testGetMedicationById() throws NotFoundException {
        // Given
        String medicationId = "1e2c4ea1-efa0-40f3-8dd4-30c5e7b74873";
        Medication expectedMedication = new Medication();
        expectedMedication.setId(medicationId);
        when(medicationRepository.findById(medicationId)).thenReturn(Optional.of(expectedMedication));

        // When
        Medication actualMedication = medicationRepository.findById(medicationId)
                .orElseThrow(
                        () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Medication not found with id: " + medicationId));

        // Then
        assertEquals(expectedMedication, actualMedication);
    }
}
