package com.amakhnev.loveholidays.flightsfinder.repository;

import com.amakhnev.loveholidays.flightsfinder.entity.City;
import com.amakhnev.loveholidays.flightsfinder.exceptions.FlightsFinderException;

import java.util.List;

public interface FlightsRepository {

    List<City> getCities() throws FlightsFinderException;

    City getCity(String name) throws FlightsFinderException;

    List<City> getDestinations(City origin) throws FlightsFinderException;

    int getPrice(City origin, City destination) throws FlightsFinderException;
}
