package com.oluyinka.droneapi.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.oluyinka.droneapi.entities.DispatchHistory;

@Repository
public interface DispatchHistoryRepository extends JpaRepository<DispatchHistory, String> {

    List<DispatchHistory> findByMedicationsId(String medicationId);

    List<DispatchHistory> findByDroneSerialNumber(String droneId);

    List<DispatchHistory> findAll();

}

