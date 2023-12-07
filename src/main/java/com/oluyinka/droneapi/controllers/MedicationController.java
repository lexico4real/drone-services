package com.oluyinka.droneapi.controllers;

import com.oluyinka.droneapi.dto.CreateMedicationDto;
import com.oluyinka.droneapi.dto.UpdateMedicationDto;
import com.oluyinka.droneapi.entities.Medication;
import com.oluyinka.droneapi.services.MedicationService;
import org.springframework.data.crossstore.ChangeSetPersister.NotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/medications")
public class MedicationController {

    private final MedicationService medicationService;

    public MedicationController(MedicationService medicationService) {
        this.medicationService = medicationService;
    }

    @PostMapping
    public ResponseEntity<Medication> createMedication(@Valid @RequestBody CreateMedicationDto createMedicationDto) {
        try {
            Medication createdMedication = medicationService.createMedication(createMedicationDto);
            return new ResponseEntity<>(createdMedication, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping
    public ResponseEntity<List<Medication>> getAllMedications() {
        List<Medication> medications = medicationService.getAllMedications();
        System.out.println("Medications:" + medications);
        return new ResponseEntity<>(medications, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public Medication getMedicationById(@PathVariable String id) throws NotFoundException {
        return medicationService.getMedicationById(id);
    }

    @PatchMapping("/{id}")
    public Medication updateMedication(@PathVariable String id,
            @Valid @RequestBody UpdateMedicationDto updateMedicationDto) throws NotFoundException {
        return medicationService.updateMedication(id, updateMedicationDto);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteMedicationById(@PathVariable String id) throws NotFoundException {
        medicationService.deleteMedicationById(id);
        return ResponseEntity.noContent().build();
    }
}
