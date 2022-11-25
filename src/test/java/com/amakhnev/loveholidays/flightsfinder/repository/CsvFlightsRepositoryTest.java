package com.amakhnev.loveholidays.flightsfinder.repository;

import com.amakhnev.loveholidays.flightsfinder.entity.City;
import com.amakhnev.loveholidays.flightsfinder.exceptions.FlightsFinderException;
import com.amakhnev.loveholidays.flightsfinder.exceptions.FlightsFinderExceptionEnum;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class CsvFlightsRepositoryTest {
    CsvFlightsRepository repository;

    @BeforeEach
    void setUp() throws FlightsFinderException {
        repository = new CsvFlightsRepository("testflights.csv");

    }

    @Test
    void whenWrongRepositoryFileName_thenErrorShouldBeThrown() {

        FlightsFinderException exception = assertThrows(FlightsFinderException.class, () -> new CsvFlightsRepository("badfilename.csv"));

        assertEquals(FlightsFinderExceptionEnum.REPO_IO.getCode(), exception.getCode());
        assertEquals(FlightsFinderExceptionEnum.REPO_IO.getMessage(), exception.getMessage());


    }

    @Test
    void whenEmptyRepositoryFile_thenErrorShouldBeThrown()  {

        FlightsFinderException exception = assertThrows(FlightsFinderException.class, () -> new CsvFlightsRepository("emptyflights.csv"));

        assertEquals(FlightsFinderExceptionEnum.REPO_NO_HEADER.getCode(), exception.getCode());
        assertEquals(FlightsFinderExceptionEnum.REPO_NO_HEADER.getMessage(), exception.getMessage());

    }

    @Test
    void whenWrongFlightsInRepositoryFile_thenErrorShouldBeThrown() {

        FlightsFinderException exception = assertThrows(FlightsFinderException.class, () -> new CsvFlightsRepository("wronglines.csv"));

        assertEquals(FlightsFinderExceptionEnum.REPO_WRONG_LINES.getCode(), exception.getCode());
        assertEquals(FlightsFinderExceptionEnum.REPO_WRONG_LINES.getMessage(), exception.getMessage());

    }

    @Test
    void whenWrongFlightStringInRepositoryFile_thenErrorShouldBeThrown() {

        FlightsFinderException exception = assertThrows(FlightsFinderException.class, () -> new CsvFlightsRepository("wrongsingleline.csv"));

        assertEquals(FlightsFinderExceptionEnum.REPO_WRONG_LINES.getCode(), exception.getCode());
        assertEquals(FlightsFinderExceptionEnum.REPO_WRONG_LINES.getMessage(), exception.getMessage());

    }


    @Test
    void whenReturnCities_thenShouldReturnTestCitiesValues() throws Exception {

        List<City> cities = repository.getCities();

        assertEquals(4, cities.size());

        for (int i=0; i<4; i++){
            assertEquals("City "+(i+1),cities.get(i).getName());
        }

    }

    @Test
    void whenCityIsNotLast_thenDestinationsShouldBeReturned() throws Exception {

        List<City> destinations = repository.getDestinations(new City("City 1"));

        assertEquals(3, destinations.size());
        for (int i=0; i<3; i++){
            assertEquals("City "+(i+2),destinations.get(i).getName());
        }

    }


    @Test
    void whenCityIsLast_thenNoDestinationsShouldBeReturned() throws Exception {

        List<City> destinations = repository.getDestinations(new City("City 4"));
        assertEquals(0, destinations.size());

    }
//
//    @Test
//    void whenOriginIsTheSameAsDestination_thenErrorShouldBeRaised() {
//
//        FlightsFinderException exception = assertThrows(FlightsFinderException.class, () -> repository.getPrice(new City("City 1"),new City("City 1")));
//
//        assertEquals(FlightsFinderExceptionEnum.REPO_WRONG_ARGS.getCode(), exception.getCode());
//        assertEquals(FlightsFinderExceptionEnum.REPO_WRONG_ARGS.getMessage(), exception.getMessage());
//
//
//        exception = assertThrows(FlightsFinderException.class, () -> repository.getPrice(new City("City 2"),new City("City 1")));
//
//        assertEquals(FlightsFinderExceptionEnum.REPO_WRONG_ARGS.getCode(), exception.getCode());
//        assertEquals(FlightsFinderExceptionEnum.REPO_WRONG_ARGS.getMessage(), exception.getMessage());
//
//
//    }

    @Test
    void whenAllArgsAreCorrect_thenPriceShouldBeFound() throws Exception {

        assertEquals(15, repository.getPrice(new City("City 1"),new City("City 2")).get());

    }

}