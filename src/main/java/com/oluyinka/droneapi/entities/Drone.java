package com.oluyinka.droneapi.entities;


import org.hibernate.annotations.GenericGenerator;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.oluyinka.droneapi.model.DroneModel;
import com.oluyinka.droneapi.utils.enums.DroneState;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@Entity
@Table(name = "drone")
@NoArgsConstructor
@AllArgsConstructor
@ToString(exclude = "dispatch")
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

    @JsonIgnore
    @OneToOne(mappedBy = "drone")
    private Dispatch dispatch;

    public Drone(String serialNumber) {
        this.serialNumber = serialNumber;
    }
    
    public Drone(DroneModel model, int batteryCapacity, double weightLimit) {
        this.model = model;
        this.batteryCapacity = batteryCapacity;
        this.weightLimit = weightLimit;
    }
}
