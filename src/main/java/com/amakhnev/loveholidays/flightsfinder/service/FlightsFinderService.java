package com.amakhnev.loveholidays.flightsfinder.service;

import com.amakhnev.loveholidays.flightsfinder.entity.City;
import com.amakhnev.loveholidays.flightsfinder.entity.Route;
import com.amakhnev.loveholidays.flightsfinder.exceptions.FlightsFinderException;
import com.amakhnev.loveholidays.flightsfinder.exceptions.FlightsFinderExceptionEnum;
import com.amakhnev.loveholidays.flightsfinder.repository.FlightsRepository;

import java.util.*;

public class FlightsFinderService {

    private final FlightsRepository repository;

    public FlightsFinderService(FlightsRepository repository) {
        this.repository = repository;
    }

    public List<Route> getRoutes(City origin, City destination) throws FlightsFinderException {

        List<Route> routes = new ArrayList<>();

        HashMap<City,Route> bestRoutes = new HashMap<>();
        routes.add(new Route(origin));

        while(!routes.isEmpty()){
            Route current = routes.remove(0);
            City last = current.getRoute().get(current.getRoute().size()-1);
            for (City next : repository.getDestinations(last)) {
                if (current.getRoute().contains(next)){ // not cycle
                    continue;
                }
                Route newRoute = new Route(current);
                newRoute.addDestination(next,repository.getPrice(last,next).get());
                if (!bestRoutes.containsKey(next) || bestRoutes.get(next).getPrice() > newRoute.getPrice()){
                    bestRoutes.put(next,newRoute);
                }
                if (!next.equals(destination)){
                    routes.add(newRoute);
                }
            }
        }

        if (bestRoutes.containsKey(destination)){
            routes.add(bestRoutes.get(destination));
        }

//        getRoutesFor(routes,new Route(origin),destination);
//
//        routes.sort(Comparator.comparingInt(Route::getPrice));

        return routes;
    }

    private void getRoutesFor(List<Route> routes, Route currentRoute, City finalDestination) throws FlightsFinderException {
        City currentDestination = currentRoute.getRoute().get(currentRoute.getRoute().size()-1);

        for (City city :
                repository.getDestinations(currentDestination)) {
            Route newRoute = new Route(currentRoute);
            newRoute.addDestination(city, repository.getPrice(currentDestination,city).orElseThrow(()->new FlightsFinderException(FlightsFinderExceptionEnum.SERVICE_WRONG_ARGS)));
            if (city.equals(finalDestination)){
                routes.add(newRoute);
            } else {
                getRoutesFor(routes,newRoute,finalDestination);
            }
        }

    }



    public Optional<Route> getCheapest(City origin, City destination) throws FlightsFinderException {
        List<Route> routes =getRoutes(origin,destination);
        return routes.isEmpty()?Optional.empty():Optional.of(routes.get(0));
    }


}
