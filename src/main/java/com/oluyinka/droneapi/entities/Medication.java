package com.oluyinka.droneapi.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.annotations.GenericGenerator;

import com.fasterxml.jackson.annotation.JsonIgnore;


@Data
@Entity
@Table
@ToString(exclude = "dispatches")
@NoArgsConstructor
@AllArgsConstructor
public class Medication {

    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    private String id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private double weight;

    @Column(nullable = false)
    private String code;

    @Column(nullable = false)
    private String image;

    @JsonIgnore
    @ManyToMany(mappedBy = "medications", fetch = FetchType.LAZY)
    private List<Dispatch> dispatches = new ArrayList<>();

    public Medication(String id){
        this.id = id;
    }
}

