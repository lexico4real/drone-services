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
import java.util.stream.Collectors;

@Service
public class DispatchService {

     private static final Logger LOGGER = LoggerFactory.getLogger(DroneService.class);

    @Autowired
    private final DispatchRepository dispatchRepository;
    @Autowired
    private final MedicationRepository medicationRepository;
    @Autowired
    private final DroneRepository droneRepository;

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
        List<Dispatch> drones = dispatchRepository.findAll();
        drones.forEach(drone -> {
            if (drone.getDrone().getState() == DroneState.DELIVERING) {
                drone.getDrone().setState(DroneState.IDLE);
                try {
                    deleteDispatchById(drone.getId());
                } catch (EntityNotFoundException | NotFoundException e) {
                    e.printStackTrace();
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
        found.setBatteryCapacity(updateDroneDto.getBatteryCapacity());
        found.setWeightLimit(updateDroneDto.getWeightLimit());
        found.setState(updateDroneDto.getState());
        return droneRepository.save(found);
    }

    @Transactional
    public Dispatch updateDroneStateWithDispatchId(String id, UpdateDroneDto updateDroneDto) throws NotFoundException {
        Dispatch found = getDispatchById(id);
        found.getDrone().setBatteryCapacity(updateDroneDto.getBatteryCapacity());
        found.getDrone().setWeightLimit(updateDroneDto.getWeightLimit());
        found.getDrone().setState(updateDroneDto.getState());
        droneRepository.save(found.getDrone());
        return found;
    }

    @Transactional
    public Dispatch createDispatch(CreateDispatchDto createDispatchDto) {
        String description = createDispatchDto.getDescription();
        Drone drone = getDroneById(createDispatchDto.getDroneId());
        List<Medication> medications = getMedicationByIds(createDispatchDto.getMedicationIds());

        System.out.println("MEDICATIONS" + medications);
        System.out.println("DRONE" + drone);

        
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
        DroneState state = droneObject.getState();

        UpdateDroneDto updateDroneDto = new UpdateDroneDto();
        updateDroneDto.setModel(model);
        updateDroneDto.setBatteryCapacity(batteryCapacity);
        updateDroneDto.setWeightLimit(weightLimit);
        updateDroneDto.setState(state);

        droneObject.setState(DroneState.LOADING);
        updateDroneState(serialNumber, updateDroneDto);
        dispatch.setMedications(medications);
        droneRepository.save(droneObject);

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
        // found.setMedications(getMedicationByIds(updateDispatchDto.getMedicationIds()));

        try {
            return dispatchRepository.save(found);
        } catch (Exception e) {
            throw new EntityNotFoundException("Drone already assigned to medication");
        }
    }

    @Transactional
    public void deleteDispatchById(String id) throws NotFoundException {
        Dispatch found = getDispatchById(id);
        // dispatchHistoryRepository.save(found);
        dispatchRepository.deleteById(found.getId());
    }
}
