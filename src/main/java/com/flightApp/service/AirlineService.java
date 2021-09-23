package com.flightApp.service;

import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;

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
	
	public Airline registerAirline(Airline details) throws BadRequestException, AirlineAlreadyFoundException{
		if(details == null) {
			logger.warn("Empty Body");
			throw new BadRequestException("Empty Body!");
		}
		if(airlineRepository.findById(details.getName()).isPresent()) {
			logger.warn("Airline details already exists!");
			throw new AirlineAlreadyFoundException("Airline details already exists!");
		}
		return airlineRepository.save(details);
	}
	
	public Airline updateAirline(String name, Airline details) throws AirlineNotFoundException{
		Airline airlineDetails = airlineRepository.findById(name).orElse(null);
		if(airlineDetails != null) {
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
		if(!airlineRepository.findById(name).isPresent()) {
			logger.warn("Airline details not found!");
			throw new AirlineNotFoundException("Airline Details not found");
		}
		airlineRepository.deleteById(name);
		
		return "Success";
	}
	
}
