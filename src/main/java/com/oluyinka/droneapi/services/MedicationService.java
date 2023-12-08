package com.oluyinka.droneapi.services;

import com.oluyinka.droneapi.dto.CreateMedicationDto;
import com.oluyinka.droneapi.dto.UpdateMedicationDto;
import com.oluyinka.droneapi.entities.Medication;
import com.oluyinka.droneapi.repositories.MedicationRepository;

import org.springframework.data.crossstore.ChangeSetPersister.NotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
public class MedicationService {

    private final MedicationRepository medicationRepository;

    public MedicationService(MedicationRepository medicationRepository) {
        this.medicationRepository = medicationRepository;
    }

    public Medication createMedication(CreateMedicationDto createMedicationDto) {
        Medication medication = new Medication();
        medication.setName(createMedicationDto.getName());
        medication.setWeight(createMedicationDto.getWeight());
        medication.setCode(createMedicationDto.getCode());
        medication.setImage(createMedicationDto.getImage());

        return medicationRepository.save(medication);
    }

    public List<Medication> getAllMedications() {
        return medicationRepository.getAllMedications();
    }

    public Medication getMedicationById(String id) throws NotFoundException {
        return medicationRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                        "Medication not found with id: " + id));
    }

    public Medication updateMedication(String id, UpdateMedicationDto updateMedicationDto) throws NotFoundException {
        Medication found = getMedicationById(id);
        found.setName(updateMedicationDto.getName());
        found.setWeight(updateMedicationDto.getWeight());
        found.setCode(updateMedicationDto.getCode());
        found.setImage(updateMedicationDto.getImage());

        return medicationRepository.save(found);
    }

    public void deleteMedicationById(String id) throws NotFoundException {
        Medication found = getMedicationById(id);
        medicationRepository.deleteById(found.getId());
    }
}
