package com.oluyinka.droneapi.entities;

import lombok.Data;
import jakarta.persistence.*;

import org.hibernate.annotations.GenericGenerator;


@Data
@Entity
@Table
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
}

