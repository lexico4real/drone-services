package com.oluyinka.droneapi.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import com.oluyinka.droneapi.dto.CreateDispatchHistoryDto;
import com.oluyinka.droneapi.dto.UpdateDroneDto;
import com.oluyinka.droneapi.entities.DispatchHistory;
import com.oluyinka.droneapi.entities.Drone;
import com.oluyinka.droneapi.entities.Medication;
import com.oluyinka.droneapi.repositories.DispatchHistoryRepository;
import com.oluyinka.droneapi.repositories.DroneRepository;
import com.oluyinka.droneapi.repositories.MedicationRepository;

import java.util.List;

@Service
public class DispatchHistoryService {

    @Autowired
    private DispatchHistoryRepository dispatchHistoryRepository;

    @Autowired
    private MedicationRepository medicationRepository;

    @Autowired
    private DroneRepository droneRepository;

    @Transactional
    public Drone updateDroneState(String id, UpdateDroneDto updateDroneDto) {
        try {
            Drone found = getDroneById(id);
            found.setBatteryCapacity(updateDroneDto.getBatteryCapacity());
            found.setWeightLimit(updateDroneDto.getWeightLimit());
            found.setState(updateDroneDto.getState());
            return droneRepository.save(found);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Drone not found with id: " + id);
        }
    }

    public DispatchHistory getDispatchHistoryById(String id) {
        DispatchHistory found = dispatchHistoryRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                        "Dispatch not found with id: " + id));
        return found;
    }

    @Transactional
    public DispatchHistory updateDroneStateWithDispatchId(String id, UpdateDroneDto updateDroneDto) {
        DispatchHistory found = getDispatchHistoryById(id);
        Drone drone = found.getDrone();
        drone.setBatteryCapacity(updateDroneDto.getBatteryCapacity());
        drone.setWeightLimit(updateDroneDto.getWeightLimit());
        drone.setState(updateDroneDto.getState());
        try {
            droneRepository.save(drone);
            return found;
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Drone not found with id: " + id);
        }
    }

    @Transactional
    public DispatchHistory createDispatchHistory(CreateDispatchHistoryDto createDispatchHistoryDto) {
        String description = createDispatchHistoryDto.getDescription();
        String droneId = createDispatchHistoryDto.getDrone().getSerialNumber();
        List<String> medicationIds = createDispatchHistoryDto.getMedicationIds();

        Drone drone = getDroneById(droneId);
        List<Medication> medications = getMedicationByIds(medicationIds);

        DispatchHistory dispatchHistory = new DispatchHistory();
        dispatchHistory.setDescription(description);
        dispatchHistory.setDrone(drone);
        dispatchHistory.setMedications(medications);

        try {
            return dispatchHistoryRepository.save(dispatchHistory);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Drone not found with id: " + droneId);
        }
    }

    private Drone getDroneById(String id) {
        return droneRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Drone not found with id: " + id));
    }

    private List<Medication> getMedicationByIds(List<String> ids) {
        List<Medication> medications = medicationRepository.findAllById(ids);
        if (medications.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Medication not found with id: " + ids);
        }
        return medications;
    }
}
