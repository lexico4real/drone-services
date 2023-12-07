package com.oluyinka.droneapi.entities;


import org.hibernate.annotations.GenericGenerator;

import com.oluyinka.droneapi.model.DroneModel;
import com.oluyinka.droneapi.utils.enums.DroneState;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "drone")
public class Drone {
    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    private String serialNumber;

    @Enumerated(EnumType.STRING)
    private DroneModel model;

    @Enumerated(EnumType.STRING)
    private DroneState state;

    @Column(nullable = false)
    private int batteryCapacity;

    @Column(nullable = false)
    private Double weightLimit;
}
