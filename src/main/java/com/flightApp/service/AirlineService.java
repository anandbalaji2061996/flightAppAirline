package com.flightApp.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.flightApp.DAO.Airline;
import com.flightApp.exception.AirlineAlreadyFoundException;
import com.flightApp.exception.AirlineNotFoundException;
import com.flightApp.exception.BadRequestException;
import com.flightApp.repository.AirlineRepository;

@Service
public class AirlineService {

	private static final Logger logger = LogManager.getLogger(AirlineService.class);

	private final AirlineRepository airlineRepository;

	public AirlineService(AirlineRepository airlineRepository) {
		this.airlineRepository = airlineRepository;
	}

	public Airline registerAirline(Airline details) throws BadRequestException, AirlineAlreadyFoundException {
		if (details == null) {
			logger.warn("Empty Body");
			throw new BadRequestException("Empty Body!");
		}
		if (airlineRepository.findById(details.getName()).isPresent()) {
			logger.warn("Airline details already exists!");
			throw new AirlineAlreadyFoundException("Airline details already exists!");
		}
		return airlineRepository.save(details);
	}

	public Airline updateAirline(String name, Airline details) throws AirlineNotFoundException {
		Airline airlineDetails = airlineRepository.findById(name).orElse(null);
		if (airlineDetails != null) {
			airlineDetails.setAddress(details.getAddress());
			airlineDetails.setContactNumber(details.getContactNumber());

			return airlineRepository.save(details);
		}
		logger.warn("Airline details not found!");
		throw new AirlineNotFoundException("Airline details not found!");
	}

	public List<Airline> getAllAirline() {
		return airlineRepository.findAll();
	}

	public List<String> getAllAirlineNames() {
		List<String> names = new ArrayList<>();
		airlineRepository.findAll().forEach(name -> names.add(name.getName()));
		return names;
	}

	public String deleteAirlineDetails(String name) throws AirlineNotFoundException {
		String baseUrl = "http://localhost:8082/admin/api/v1.0/flight/airline/airlineDelete/" + name;

		RestTemplate restTemplate = new RestTemplate();
		ResponseEntity<String> response = null;
		try {
			response = restTemplate.exchange(baseUrl, HttpMethod.DELETE, getHeaders(), String.class);
			System.out.println(response.getBody());
			if (!airlineRepository.findById(name).isPresent()) {
				logger.warn("Airline details not found!");
			}
			airlineRepository.deleteById(name);

			return "Success";
		} catch (Exception ex) {
			throw new AirlineNotFoundException(ex.getMessage());
		}

	}

	private static HttpEntity<?> getHeaders() throws IOException {
		HttpHeaders headers = new HttpHeaders();
		headers.set("Accept", MediaType.APPLICATION_JSON_VALUE);
		return new HttpEntity<>(headers);
	}

}
