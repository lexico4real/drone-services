package com.oluyinka.droneapi.entities;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.List;

import org.hibernate.annotations.GenericGenerator;

@Data
@Entity
@Table
@ToString(exclude = "drone")
@NoArgsConstructor
public class DispatchHistory {

    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    private String id;

    @Column(name = "description", nullable = false)
    private String description;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "drone_id")
    private Drone drone;

    @ManyToMany
    @JoinTable(name = "dispatch_history_medications", joinColumns = @JoinColumn(name = "dispatch_history_id"), inverseJoinColumns = @JoinColumn(name = "medication_id"))
    private List<Medication> medications;

    @Column(name = "is_completed", nullable = false)
    private boolean isCompleted = false;

    public DispatchHistory(String id) {
        this.id = id;
    }
}
