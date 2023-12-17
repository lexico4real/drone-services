package com.oluyinka.droneapi.controllers;

import com.oluyinka.droneapi.dto.*;

import com.oluyinka.droneapi.entities.Dispatch;
import com.oluyinka.droneapi.services.DispatchService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.crossstore.ChangeSetPersister.NotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/dispatch")
public class DispatchController {

    @Autowired
    private final DispatchService dispatchService;

    public DispatchController(DispatchService dispatchService) {
        this.dispatchService = dispatchService;
    }

    @GetMapping("/withmedication/{id}")
    public List<Dispatch> findByMedicationId(@PathVariable String id) {
        return dispatchService.findByMedicationId(id);
    }

    @GetMapping("/withdrone/{id}")
    public List<Dispatch> findByDroneId(@PathVariable String id) {
        return dispatchService.findByDroneId(id);
    }

    @PostMapping
    public ResponseEntity<Dispatch> createDispatch(@RequestBody @Valid CreateDispatchDto createDispatchDto) {
        Dispatch createdDispatch = dispatchService.createDispatch(createDispatchDto);
        return new ResponseEntity<>(createdDispatch, HttpStatus.CREATED);
    }

    @GetMapping
    public List<Dispatch> getAllDispatches() {
        return dispatchService.getAllDispatches();
    }

    @GetMapping("/{id}")
    public Dispatch getDispatchById(@PathVariable String id) throws NotFoundException {
        return dispatchService.getDispatchById(id);
    }

    @PatchMapping("/dronestate/{id}")
    public Dispatch updateDroneStateWithDispatchId(
            @PathVariable String id,
            @RequestBody UpdateDroneDto updateDroneDto) throws NotFoundException {
        return dispatchService.updateDroneStateWithDispatchId(id, updateDroneDto);
    }

    @PatchMapping("/{id}")
    public Dispatch update(
            @PathVariable String id,
            @RequestBody UpdateDispatchDto updateDispatchDto) throws NotFoundException {
        return dispatchService.updateDispatch(id, updateDispatchDto);
    }

    @DeleteMapping("/{id}")
    public void deleteDispatch(@PathVariable String id) throws NotFoundException {
        dispatchService.deleteDispatchById(id);
    }
}

