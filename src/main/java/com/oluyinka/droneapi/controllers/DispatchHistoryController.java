package com.oluyinka.droneapi.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.oluyinka.droneapi.dto.CreateDispatchHistoryDto;
import com.oluyinka.droneapi.entities.Dispatch;
import com.oluyinka.droneapi.entities.DispatchHistory;
import com.oluyinka.droneapi.services.DispatchHistoryService;


@RestController
@RequestMapping("/api/dispatch-histories")
public class DispatchHistoryController {
    
    @Autowired
    private final DispatchHistoryService dispatchHistoryService;

    @Autowired
    public DispatchHistoryController(DispatchHistoryService dispatchHistoryService) {
        this.dispatchHistoryService = dispatchHistoryService;
    }

    @GetMapping
    public List<DispatchHistory> getAllDispatchHistories() {
        return dispatchHistoryService.getAllDispatchHistories();
    }

    // public CreateDispatchHistoryDto convertToDTO(DispatchHistory dispatchHistory) {
    //     CreateDispatchHistoryDto dto = new CreateDispatchHistoryDto();
    //     dto.setId(dispatchHistory.getId());
    //     dto.setDescription(dispatchHistory.getDescription());
    //     dto.setDrone(dispatchHistory.getDrone());
    //     dto.setMedications(dispatchHistory.getMedications());
        
    //     // Map other fields

    //     return dto;
    // }
}
