package com.flightApp.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import com.flightApp.DAO.Airline;
import com.flightApp.exception.AirlineAlreadyFoundException;
import com.flightApp.exception.AirlineNotFoundException;
import com.flightApp.exception.BadRequestException;
import com.flightApp.repository.AirlineRepository;

@DataJpaTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
public class AirlineDetailServiceTest {

	@Autowired
	AirlineRepository airlineInterface;

	private AirlineService service;

	private Airline details;

	@BeforeEach
	public void setup1() {
		service = new AirlineService(airlineInterface);

		details = new Airline();
		details.setName("airlineName");
		details.setAddress("airlineAddress");
		details.setContactNumber("airlineContactNumber");
	}

	@AfterEach
	public void setup2() {
		airlineInterface.deleteAll();
	}

	@Test
	public void registerAirlineTest() throws BadRequestException, AirlineAlreadyFoundException {
		Throwable thrown = catchThrowable(() -> service.registerAirline(null));

		assertThat(thrown).isInstanceOf(BadRequestException.class);

		Airline response = service.registerAirline(details);

		assertNotNull(response);
		assertEquals("airlineName", response.getName());
		assertEquals("airlineAddress", response.getAddress());
		assertEquals("airlineContactNumber", response.getContactNumber());

		thrown = catchThrowable(() -> service.registerAirline(details));

		assertThat(thrown).isInstanceOf(AirlineAlreadyFoundException.class);

	}

	@Test
	public void updateAirlineTest()
			throws AirlineNotFoundException, BadRequestException, AirlineAlreadyFoundException {
		Throwable thrown = catchThrowable(() -> service.updateAirline("test1", null));

		assertThat(thrown).isInstanceOf(AirlineNotFoundException.class);

		service.registerAirline(details);

		details.setAddress("airlineAddress1");
		details.setContactNumber("airlineContactNumber1");

		Airline response = service.updateAirline("airlineName", details);

		assertNotNull(response);
		assertEquals("airlineAddress1", response.getAddress());
		assertEquals("airlineContactNumber1", response.getContactNumber());
	}

	@Test
	public void getAllAirlineTest() throws BadRequestException, AirlineAlreadyFoundException {
		service.registerAirline(details);

		assertTrue(service.getAllAirline().size() >= 1);
	}
	
	@Test
	public void getAllAirlineNamesTest() throws BadRequestException, AirlineAlreadyFoundException {
		service.registerAirline(details);

		assertTrue(service.getAllAirlineNames().get(0).equals("airlineName"));
	}
	
	@Test
	public void deleteAirlineTest() throws AirlineNotFoundException, BadRequestException, AirlineAlreadyFoundException {
		Airline response = service.registerAirline(details);
		assertNotNull(airlineInterface.findById(response.getName()));
		
		assertEquals("Success", service.deleteAirlineDetails(response.getName()));
		assertFalse(airlineInterface.findById(response.getName()).isPresent());
	}
	
}
