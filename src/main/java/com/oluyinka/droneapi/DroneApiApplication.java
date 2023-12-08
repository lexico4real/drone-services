package com.oluyinka.droneapi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class DroneApiApplication {

	public static void main(String[] args) {
		SpringApplication.run(DroneApiApplication.class, args);
	}

}
