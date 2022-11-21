package com.amakhnev.loveholidays.flightsfinder.service;

import com.amakhnev.loveholidays.flightsfinder.entity.Route;
import com.amakhnev.loveholidays.flightsfinder.exceptions.FlightsFinderException;
import com.amakhnev.loveholidays.flightsfinder.repository.FlightsRepository;

import java.util.List;

public class FlightsFinderService {

    private final FlightsRepository repository;

    public FlightsFinderService(FlightsRepository repository) {
        this.repository = repository;
    }

    public List<Route> getRoutes(String originString, String destinationString) throws FlightsFinderException {
        return null;
    }


}
