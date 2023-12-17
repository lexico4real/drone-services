package com.oluyinka.droneapi.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.crossstore.ChangeSetPersister.NotFoundException;

import com.oluyinka.droneapi.dto.UpdateMedicationDto;
import com.oluyinka.droneapi.entities.Medication;
import com.oluyinka.droneapi.repositories.MedicationRepository;

@ExtendWith(MockitoExtension.class)
public class MedicationServiceTest {

    @Mock
    private MedicationRepository medicationRepository;

    @InjectMocks
    private MedicationService medicationService;

    @BeforeEach
    void setUp() {
        medicationRepository = mock(MedicationRepository.class);
        medicationService = new MedicationService(medicationRepository);
    }

    @Test
    void testDeleteMedicationById() throws NotFoundException {
        // Given
        String medicationId = "c70a499e-46a9-408c-a0ab-43e4c2ed70b1";
        Medication existingMedication = new Medication();
        when(medicationRepository.findById(medicationId)).thenReturn(Optional.of(existingMedication));

        // When
        medicationService.deleteMedicationById(medicationId);

        // Then
        Mockito.verify(medicationRepository, Mockito.times(1)).deleteById(existingMedication.getId());
    }

    @Test
    void testUpdateMedication() throws NotFoundException {
        // Given
        String medicationId = "147d9c4a-dcbc-4803-b20c-20c49eeacd9e";
        UpdateMedicationDto updateMedicationDto = new UpdateMedicationDto();
        updateMedicationDto.setName("UpdatedMed1");
        updateMedicationDto.setWeight(12.0);
        updateMedicationDto.setCode("XYZ789");
        updateMedicationDto.setImage("updated-image-url");

        Medication existingMedication = new Medication();
        when(medicationRepository.findById(medicationId)).thenReturn(Optional.of(existingMedication));

        when(medicationRepository.save(any())).thenAnswer(invocation -> {
            Object argument = invocation.getArgument(0);
            if (argument instanceof Medication) {
                return (Medication) argument;
            }
            return null;
        });

        // When
        Medication updatedMedication = medicationService.updateMedication(medicationId, updateMedicationDto);

        // Then
        assertEquals(updateMedicationDto.getName(), updatedMedication.getName());
        assertEquals(updateMedicationDto.getWeight(), updatedMedication.getWeight());
        assertEquals(updateMedicationDto.getCode(), updatedMedication.getCode());
        assertEquals(updateMedicationDto.getImage(), updatedMedication.getImage());

        Mockito.verify(medicationRepository, Mockito.times(1)).save(updatedMedication);
    }
}
