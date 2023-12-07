package com.oluyinka.droneapi.entities;

import lombok.Data;

import jakarta.persistence.*;

import org.hibernate.annotations.GenericGenerator;

import java.util.List;

@Data
@Entity
@Table
public class Dispatch {

    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    private String id;

    @Column(nullable = true)
    private String description;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "drone_serial_number", referencedColumnName = "serialNumber")
    private Drone drone;

    @ManyToMany()
    @JoinTable(
            name = "dispatch_medications",
            joinColumns = @JoinColumn(name = "dispatch_id"),
            inverseJoinColumns = @JoinColumn(name = "medication_id")
    )
    private List<Medication> medications;

    @Column(nullable = false)
    private boolean isCompleted;
}


