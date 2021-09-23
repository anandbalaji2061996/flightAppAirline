package com.flightApp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.flightApp.DAO.Airline;

@Repository
public interface AirlineRepository extends JpaRepository<Airline, String> {

}
