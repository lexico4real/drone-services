package com.oluyinka.droneapi.entities;

import lombok.Data;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;

import org.hibernate.annotations.GenericGenerator;


@Data
@Entity
@Table
public class Medication {

    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    private String id;

    @NotNull
    private String name;

    @NotNull
    private double weight;

    @NotNull
    @Column(unique = true)
    private String code;

    @NotNull
    private String image;
}

