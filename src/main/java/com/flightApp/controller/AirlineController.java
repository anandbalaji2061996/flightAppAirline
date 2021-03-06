package com.flightApp.controller;

import java.util.List;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

@CrossOrigin("*")
@RestController
@RequestMapping("/api3/v1.0/admin/airline")
public class AirlineController {

    private static final Logger logger = LoggerFactory.getLogger(AirlineController.class);
    
    @Autowired
    private AirlineService service;
    
    @PostMapping(path = "/register", produces = {"application/json"})
    public ResponseEntity<Airline> registerAirline(@Valid @RequestBody Airline airline) throws BadRequestException, AirlineAlreadyFoundException {
    	logger.info("Register Airline");
    	return new ResponseEntity<Airline>(service.registerAirline(airline), HttpStatus.CREATED);    	
    }
    
    @GetMapping(path = "/all", produces = {"application/json"})
    public ResponseEntity<List<Airline>> getAllAirline() {
    	logger.info("Get all Airline");
    	return new ResponseEntity<>(service.getAllAirline(), HttpStatus.OK);
    }
    
    @GetMapping(path = "/names", produces = {"application/json"})
    public ResponseEntity<List<String>> getAirlineNames() {
    	logger.info("Get Airline Names");
    	return new ResponseEntity<List<String>>(service.getAllAirlineNames(), HttpStatus.OK);
    }
    
    @GetMapping(path = "/names/{name}", produces = {"application/json"})
    public ResponseEntity<Airline> getAirlineByName(@Valid @PathVariable("name") String name) {
    	logger.info("Get Airline by name" + name);
    	return new ResponseEntity<>(service.getAirlineByName(name), HttpStatus.OK);
    }
    
    @PutMapping(path = "/update/{name}", produces = {"application/json"})
    public ResponseEntity<Airline> updateAirline(@Valid @PathVariable("name") String name, @RequestBody Airline airline) throws AirlineNotFoundException {
    	logger.info("Update Airline :" + name);
    	return new ResponseEntity<Airline>(service.updateAirline(name, airline),HttpStatus.ACCEPTED);    	
    }
    
    @DeleteMapping(path = "/delete/{name}", produces = {"application/text"})
    public ResponseEntity<String> deleteAirline(@Valid @PathVariable("name") String name) throws AirlineNotFoundException {
    	logger.info("Delete Airline :" + name);
    	return new ResponseEntity<>(service.deleteAirlineDetails(name),HttpStatus.OK);    	
    }

}
