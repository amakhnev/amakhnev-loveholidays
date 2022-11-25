package com.amakhnev.loveholidays.flightsfinder.repository;

import com.amakhnev.loveholidays.flightsfinder.entity.City;
import com.amakhnev.loveholidays.flightsfinder.exceptions.FlightsFinderException;

import java.util.List;
import java.util.Optional;

public interface FlightsRepository {

    List<City> getCities();

    Optional<City> getCity(String name);

    List<City> getDestinations(City origin);

    Optional<Integer> getPrice(City origin, City destination);
}
