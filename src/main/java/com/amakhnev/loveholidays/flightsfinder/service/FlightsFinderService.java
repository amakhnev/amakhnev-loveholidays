package com.amakhnev.loveholidays.flightsfinder.service;

import com.amakhnev.loveholidays.flightsfinder.entity.City;
import com.amakhnev.loveholidays.flightsfinder.entity.Route;
import com.amakhnev.loveholidays.flightsfinder.exceptions.FlightsFinderException;
import com.amakhnev.loveholidays.flightsfinder.repository.FlightsRepository;

import java.util.List;

public class FlightsFinderService {

    private final FlightsRepository repository;

    public FlightsFinderService(FlightsRepository repository) {
        this.repository = repository;
    }

    public List<Route> getRoutes(City origin, City destination) throws FlightsFinderException {
        return null;
    }


}
