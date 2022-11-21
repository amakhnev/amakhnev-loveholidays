package com.amakhnev.loveholidays.flightsfinder.repository;

import com.amakhnev.loveholidays.flightsfinder.entity.City;
import com.amakhnev.loveholidays.flightsfinder.exceptions.FlightsFinderException;

import java.util.List;
import java.util.Optional;

public interface FlightsRepository {

    List<City> getCities() throws FlightsFinderException;

    Optional<City> getCity(String name) throws FlightsFinderException;

    List<City> getDestinations(City origin) throws FlightsFinderException;

    int getPrice(City origin, City destination) throws FlightsFinderException;
}
