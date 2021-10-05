package com.flightApp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EntityScan(basePackages = "com.flightApp")
@EnableJpaRepositories(basePackages = "com.flightApp.repository")
@EnableEurekaClient
public class FlightAppAirlineServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(FlightAppAirlineServiceApplication.class, args);
	}

}
