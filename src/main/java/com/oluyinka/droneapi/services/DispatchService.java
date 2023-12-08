package com.oluyinka.droneapi.services;

import com.oluyinka.droneapi.dto.*;
import com.oluyinka.droneapi.entities.*;
import com.oluyinka.droneapi.model.DroneModel;
import com.oluyinka.droneapi.repositories.*;

import com.oluyinka.droneapi.utils.enums.DroneState;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.crossstore.ChangeSetPersister.NotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class DispatchService {

    private static final Logger LOGGER = LoggerFactory.getLogger(DroneService.class);

    @Autowired
    private final DispatchRepository dispatchRepository;
    @Autowired
    private final MedicationRepository medicationRepository;
    @Autowired
    private final DroneRepository droneRepository;

    @Autowired
    private DispatchHistoryRepository dispatchHistoryRepository;

    public DispatchService(DispatchRepository dispatchRepository,
            MedicationRepository medicationRepository,
            DroneRepository droneRepository) {
        this.dispatchRepository = dispatchRepository;
        this.medicationRepository = medicationRepository;
        this.droneRepository = droneRepository;
    }

    @Transactional
    public List<Dispatch> findByMedicationId(String medicationId) {
        return dispatchRepository.findByMedicationId(medicationId);
    }

    @Transactional
    public List<Dispatch> findByDroneId(String droneId) {
        return dispatchRepository.findByDroneId(droneId);
    }

    @Transactional
    public Drone getDroneById(String id) {
        return droneRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Drone with id " + id + " not found"));
    }

    @Scheduled(cron = "*/5 * * * * *")
    public void resetDroneState() {
        List<Dispatch> dispatches = dispatchRepository.findAll();
        dispatches.forEach(dispatch -> {
            if (dispatch.getDrone().getState() == DroneState.DELIVERED) {
                dispatch.getDrone().setState(DroneState.IDLE);
                droneRepository.save(dispatch.getDrone());

                try {
                    DispatchHistory history = new DispatchHistory();
                    history.setDescription(dispatch.getDescription());
                    history.setDrone(dispatch.getDrone());
                    history.setMedications(dispatch.getMedications());
                    history.setCompleted(dispatch.isCompleted());
                    dispatchHistoryRepository.save(history);

                    deleteDispatchById(dispatch.getId());
                } catch (EntityNotFoundException | NotFoundException e) {
                    return;
                }
            }
        });
        LOGGER.info("Reset drones with state delivered");
    }

    @Transactional
    public List<Medication> getMedicationByIds(List<String> ids) {
        List<Medication> found = medicationRepository.findAllById(ids);
        if (found.isEmpty()) {
            throw new EntityNotFoundException("Medications not found");
        }
        return found;
    }

    @Transactional
    public Drone updateDroneState(String id, UpdateDroneDto updateDroneDto) {
        Drone found = getDroneById(id);
        if (updateDroneDto.getBatteryCapacity() != null) {
            found.setBatteryCapacity(updateDroneDto.getBatteryCapacity());
        }
        if (updateDroneDto.getWeightLimit() != null) {
            found.setWeightLimit(updateDroneDto.getWeightLimit());
        }
        if (updateDroneDto.getState() != null) {
            found.setState(updateDroneDto.getState());
        }
        return droneRepository.save(found);
    }

    @Transactional
    public Dispatch updateDroneStateWithDispatchId(String id, UpdateDroneDto updateDroneDto) throws NotFoundException {
        Dispatch found = getDispatchById(id);
        Drone drone = found.getDrone();

        if (updateDroneDto.getBatteryCapacity() != null) {
            drone.setBatteryCapacity(updateDroneDto.getBatteryCapacity());
        }

        if (updateDroneDto.getWeightLimit() != null) {
            drone.setWeightLimit(updateDroneDto.getWeightLimit());
        }

        if (updateDroneDto.getState() != null) {
            drone.setState(updateDroneDto.getState());
            found.setCompleted(true);
        }
        return found;
    }

    @Transactional
    public Dispatch createDispatch(CreateDispatchDto createDispatchDto) {
        String description = createDispatchDto.getDescription();
        Drone drone = getDroneById(createDispatchDto.getDroneId());
        List<Medication> medications = getMedicationByIds(createDispatchDto.getMedicationIds());

        if (dispatchRepository.existsByDroneSerialNumber(createDispatchDto.getDroneId())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                    "Drone with ID " + createDispatchDto.getDroneId() + " is already assigned to a dispatch.");
        }

        Dispatch dispatch = new Dispatch();
        dispatch.setDescription(description);
        dispatch.setDrone(drone);
        dispatch.setMedications(medications);

        double weightSum = medications.stream()
                .mapToDouble(medication -> medication.getWeight())
                .sum();

        Drone droneObject = getDroneById(createDispatchDto.getDroneId());

        if (droneObject.getWeightLimit() < weightSum) {
            throw new EntityNotFoundException("Drone weight not enough");
        }

        if (droneObject.getBatteryCapacity() <= 25) {
            throw new EntityNotFoundException("Drone battery capacity not enough");
        }

        String serialNumber = droneObject.getSerialNumber();
        DroneModel model = droneObject.getModel();
        Integer batteryCapacity = droneObject.getBatteryCapacity();
        Double weightLimit = droneObject.getWeightLimit();
        // DroneState state = droneObject.getState();

        UpdateDroneDto updateDroneDto = new UpdateDroneDto();
        updateDroneDto.setModel(model);
        updateDroneDto.setBatteryCapacity(batteryCapacity);
        updateDroneDto.setWeightLimit(weightLimit);
        updateDroneDto.setState(DroneState.LOADING);

        droneObject.setState(DroneState.LOADING);
        droneRepository.save(droneObject);
        updateDroneState(serialNumber, updateDroneDto);
        dispatch.setMedications(medications);

        try {
            return dispatchRepository.save(dispatch);
        } catch (Exception e) {
            throw new EntityNotFoundException("Drone already assigned to medication");
        }
    }

    @Transactional
    public List<Dispatch> getAllDispatches() {
        return dispatchRepository.findAll();
    }

    @Transactional
    public Dispatch getDispatchById(String id) throws NotFoundException {
        return dispatchRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                        "Dispatch with id " + id + " not found"));
    }

    @Transactional
    public Dispatch updateDispatch(String id, UpdateDispatchDto updateDispatchDto) throws NotFoundException {
        Dispatch found = getDispatchById(id);
        found.setDescription(updateDispatchDto.getDescription());
        found.setDrone(updateDispatchDto.getDrone());
        found.setMedications(getMedicationByIds(updateDispatchDto.getMedicationIds()));

        try {
            return dispatchRepository.save(found);
        } catch (Exception e) {
            throw new EntityNotFoundException("Drone already assigned to medication");
        }
    }

    // private DispatchHistory getDispatchHistoryById(String id) {
    //     return dispatchHistoryRepository.findById(id).orElse(null);
    // }

    @Transactional
    public void deleteDispatchById(String id) throws NotFoundException {
        Dispatch found = getDispatchById(id);
        // DispatchHistory history = new DispatchHistory();
        // dispatchHistoryRepository.save(history);
        dispatchRepository.deleteById(found.getId());
    }
}
