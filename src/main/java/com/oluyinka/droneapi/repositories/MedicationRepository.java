package com.oluyinka.droneapi.repositories;

import com.oluyinka.droneapi.dto.CreateMedicationDto;
import com.oluyinka.droneapi.entities.Medication;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Repository;
import org.springframework.web.server.ResponseStatusException;


@Repository
public interface MedicationRepository extends JpaRepository<Medication, String> {

    default Medication createMedication(CreateMedicationDto createMedicationDto) {
        try {
            Medication medication = new Medication();
            medication.setName(createMedicationDto.getName());
            medication.setWeight(createMedicationDto.getWeight());
            medication.setCode(createMedicationDto.getCode());
            medication.setImage(createMedicationDto.getImage());
            return save(medication);
        } catch (Exception e) {
            throw new RuntimeException("Error creating medication", e);
        }
    }

    default Medication getMedicationById(String id) {
        try {
            Medication found = findById(id)
                    .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Medication not found with id: " + id));
            return found;
        } catch (Exception e) {
            throw new RuntimeException("Error getting medication by ID", e);
        }
    }

    public default List<Medication> getAllMedications() {
        List<Medication> medications = this.findAll();
        return medications;
    }
}

