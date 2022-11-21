package com.amakhnev.loveholidays.flightsfinder.service;

import com.amakhnev.loveholidays.flightsfinder.entity.City;
import com.amakhnev.loveholidays.flightsfinder.entity.Route;
import com.amakhnev.loveholidays.flightsfinder.exceptions.FlightsFinderException;
import com.amakhnev.loveholidays.flightsfinder.repository.FlightsRepository;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class FlightsFinderService {

    private final FlightsRepository repository;

    public FlightsFinderService(FlightsRepository repository) {
        this.repository = repository;
    }

    public List<Route> getRoutes(City origin, City destination) throws FlightsFinderException {

        List<Route> routes = new ArrayList<>();

        getRoutesFor(routes,new Route(origin),destination);

        routes.sort(Comparator.comparingInt(Route::getPrice));

        return routes;
    }

    private void getRoutesFor(List<Route> routes, Route currentRoute, City finalDestination) throws FlightsFinderException {
        City currentDestination = currentRoute.getRoute().get(currentRoute.getRoute().size()-1);

        for (City city :
                repository.getDestinations(currentDestination)) {
            Route newRoute = new Route(currentRoute);
            newRoute.addDestination(city, repository.getPrice(currentDestination,city));
            if (city.equals(finalDestination)){
                routes.add(newRoute);
            } else {
                getRoutesFor(routes,newRoute,finalDestination);
            }
        }

    }




}
