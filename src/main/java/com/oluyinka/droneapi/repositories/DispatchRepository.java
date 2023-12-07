package com.oluyinka.droneapi.repositories;

import com.oluyinka.droneapi.entities.Dispatch;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DispatchRepository extends JpaRepository<Dispatch, String> {

    @Query("SELECT d FROM Dispatch d " +
            "JOIN FETCH d.medications m " +
            "JOIN FETCH d.drone dr " +
            "WHERE m.id = :medicationId")
    List<Dispatch> findByMedicationId(String medicationId);

    @Query("SELECT d FROM Dispatch d " +
            "JOIN FETCH d.drone dr " +
            "JOIN FETCH d.medications m " +
            "WHERE dr.serialNumber = :droneId")
    List<Dispatch> findByDroneId(String droneId);

    @Query("SELECT d FROM Dispatch d " +
            "JOIN FETCH d.drone dr " +
            "JOIN FETCH d.medications m")
    List<Dispatch> findAllWithDrone();
}
