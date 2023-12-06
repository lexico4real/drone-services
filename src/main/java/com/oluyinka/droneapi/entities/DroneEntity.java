package com.oluyinka.droneapi.entities;

import org.hibernate.annotations.GenericGenerator;

import com.oluyinka.droneapi.model.DroneModel;
import com.oluyinka.droneapi.utils.enums.DroneState;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table
public class DroneEntity {
    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    private String serialNumber;

    @Enumerated(EnumType.STRING)
    private DroneModel model;

    @Enumerated(EnumType.STRING)
    private DroneState state;

    private int batteryCapacity;

    private double weightLimit;

    // @OneToOne(mappedBy = "drone")
    // private Dispatch dispatch;

    // @JsonIgnore
    // @OneToMany(mappedBy = "drone", cascade = CascadeType.ALL)
    // private List<DispatchHistory> dispatchHistories;
}
