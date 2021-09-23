package com.flightApp.controller;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.flightApp.DAO.Airline;
import com.flightApp.exception.AirlineAlreadyFoundException;
import com.flightApp.exception.AirlineNotFoundException;
import com.flightApp.exception.BadRequestException;
import com.flightApp.service.AirlineService;

@CrossOrigin("http://localhost:4200")
@RestController
@RequestMapping("/admin/api/v1.0/airline")
public class AirlineController {

    private static final Logger logger = LogManager.getLogger(AirlineController.class);
    
    @Autowired
    private AirlineService service;
    
    @PostMapping("/register")
    public ResponseEntity<Airline> registerAirline(@RequestBody Airline airline) throws BadRequestException, AirlineAlreadyFoundException {
    	logger.info("Register Airline");
    	return new ResponseEntity<Airline>(service.registerAirline(airline), HttpStatus.CREATED);    	
    }
    
    @GetMapping("/all")
    public ResponseEntity<List<Airline>> getAllAirline() {
    	logger.info("Get all Airline");
    	return new ResponseEntity<>(service.getAllAirline(), HttpStatus.OK);
    }
    
    @GetMapping("/names")
    public ResponseEntity<List<String>> getAirlineNames() {
    	logger.info("Get Airline Names");
    	return new ResponseEntity<List<String>>(service.getAllAirlineNames(), HttpStatus.OK);
    }
    
    @PutMapping("/update/{name}")
    public ResponseEntity<Airline> updateAirline(@PathVariable("name") String name, @RequestBody Airline airline) throws AirlineNotFoundException {
    	logger.info("Update Airline :" + name);
    	return new ResponseEntity<Airline>(service.updateAirline(name, airline),HttpStatus.ACCEPTED);    	
    }
    
    @DeleteMapping("/delete/{name}")
    public ResponseEntity<String> deleteAirline(@PathVariable("name") String name) throws AirlineNotFoundException {
    	logger.info("Delete Airline :" + name);
    	return new ResponseEntity<>(service.deleteAirlineDetails(name),HttpStatus.OK);    	
    }

}
