package com.amakhnev.loveholidays.flightsfinder.service;

import com.amakhnev.loveholidays.flightsfinder.entity.City;
import com.amakhnev.loveholidays.flightsfinder.entity.Route;
import com.amakhnev.loveholidays.flightsfinder.repository.CsvFlightsRepository;
import com.amakhnev.loveholidays.flightsfinder.repository.FlightsRepository;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class FlightsFinderServiceTest {
    FlightsFinderService service;
    FlightsRepository repository;
    City origin;
    City destination;

    @BeforeEach
    void setUp() {
        repository = new CsvFlightsRepository("testflights.csv");
        service = new FlightsFinderService(repository);
    }


    @Test
    void whenOneDestinationAvailable_whenOneRouteShouldBeReturned() throws Exception{
        origin = repository.getCity("City 3").orElseThrow();
        destination = repository.getCity("City 4").orElseThrow();

        List<Route> routes = service.getRoutes(origin,destination);

        assertEquals(1,routes.size());
        assertEquals(2,routes.get(0).getRoute().size());

        assertEquals(70,routes.get(0).getPrice());


    }

    @Test
    void whenTwoDifferentRoutesAvailable_whenTwoRouteShouldBeReturnedCheapestFirst() throws Exception{
        origin = repository.getCity("City 2").orElseThrow();
        destination = repository.getCity("City 4").orElseThrow();

        List<Route> routes = service.getRoutes(origin,destination);

        assertEquals(2,routes.size());
        // City 2 -> City 4 , price 50
        assertEquals(2,routes.get(0).getRoute().size());
        assertEquals(50,routes.get(0).getPrice());
        // City 2 -> City 3 -> City 4 , price 110
        assertEquals(3,routes.get(1).getRoute().size());
        assertEquals(110,routes.get(1).getPrice());

    }

    @Test
    void whenNoRoutesExists_whenEmptyArrayShouldBeReturned() throws Exception{
        origin = repository.getCity("City 4").orElseThrow();
        destination = repository.getCity("City 4").orElseThrow();

        List<Route> routes = service.getRoutes(origin,destination);

        assertEquals(0,routes.size());

    }

    @Test
    void whenBadRouteSelected_whenEmptyArrayShouldBeReturned() throws Exception{
        origin = repository.getCity("City 4").orElseThrow();
        destination = repository.getCity("City 1").orElseThrow();

        List<Route> routes = service.getRoutes(origin,destination);

        assertEquals(0,routes.size());

    }
}