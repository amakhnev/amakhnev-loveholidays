package com.amakhnev.loveholidays.flightsfinder.service;

import com.amakhnev.loveholidays.flightsfinder.entity.City;
import com.amakhnev.loveholidays.flightsfinder.entity.Route;
import com.amakhnev.loveholidays.flightsfinder.exceptions.FlightsFinderException;
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
    void setUp() throws FlightsFinderException {
        repository = new CsvFlightsRepository("testflights.csv");
        service = new FlightsFinderService(repository);
    }


    @Test
    void whenOneDestinationAvailable_thenOneRouteShouldBeReturned() throws Exception{
        origin = repository.getCity("City 3").orElseThrow();
        destination = repository.getCity("City 4").orElseThrow();

        List<Route> routes = service.getRoutes(origin,destination);

        assertEquals(1,routes.size());
        assertEquals(2,routes.get(0).getRoute().size());

        assertEquals(70,routes.get(0).getPrice());


    }


    @Test
    void whenNoRoutesExists_thenEmptyArrayShouldBeReturned() throws Exception{
        origin = repository.getCity("City 4").orElseThrow();
        destination = repository.getCity("City 4").orElseThrow();

        List<Route> routes = service.getRoutes(origin,destination);

        assertEquals(0,routes.size());

    }

    @Test
    void whenBadRouteSelected_thenEmptyArrayShouldBeReturned() throws Exception{
        origin = repository.getCity("City 4").orElseThrow();
        destination = repository.getCity("City 1").orElseThrow();

        List<Route> routes = service.getRoutes(origin,destination);

        assertEquals(0,routes.size());

    }

    @Test
    void whenTwoDifferentRoutesAvailable_thenCheapestBeReturned () throws FlightsFinderException {
        origin = repository.getCity("City 2").orElseThrow();
        destination = repository.getCity("City 4").orElseThrow();

        Route route = service.getCheapest(origin, destination).get();

        assertEquals(50, route.getPrice());
    }

}