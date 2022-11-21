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
    void setUp() {
        repository = new CsvFlightsRepository("testflights.csv");

    }

    @Test
    void whenWrongRepositoryFileName_thenErrorShouldBeThrown() {
        repository = new CsvFlightsRepository("badfilename.csv");

        FlightsFinderException exception = assertThrows(FlightsFinderException.class, () -> repository.getCities());

        assertEquals(FlightsFinderExceptionEnum.REPO_IO.getCode(), exception.getCode());
        assertEquals(FlightsFinderExceptionEnum.REPO_IO.getMessage(), exception.getMessage());


    }

    @Test
    void whenEmptyRepositoryFile_thenErrorShouldBeThrown() {
        repository = new CsvFlightsRepository("emptyflights.csv");

        FlightsFinderException exception = assertThrows(FlightsFinderException.class, () -> repository.getCities());

        assertEquals(FlightsFinderExceptionEnum.REPO_NO_HEADER.getCode(), exception.getCode());
        assertEquals(FlightsFinderExceptionEnum.REPO_NO_HEADER.getMessage(), exception.getMessage());

    }

    @Test
    void whenWrongFlightsInRepositoryFile_thenErrorShouldBeThrown() {
        repository = new CsvFlightsRepository("wronglines.csv");

        FlightsFinderException exception = assertThrows(FlightsFinderException.class, () -> repository.getCities());

        assertEquals(FlightsFinderExceptionEnum.REPO_WRONG_LINES.getCode(), exception.getCode());
        assertEquals(FlightsFinderExceptionEnum.REPO_WRONG_LINES.getMessage(), exception.getMessage());

    }

    @Test
    void whenWrongFlightStringInRepositoryFile_thenErrorShouldBeThrown() {
        repository = new CsvFlightsRepository("wrongsingleline.csv");

        FlightsFinderException exception = assertThrows(FlightsFinderException.class, () -> repository.getCities());

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


}