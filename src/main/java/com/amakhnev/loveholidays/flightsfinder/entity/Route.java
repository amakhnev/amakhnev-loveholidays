package com.amakhnev.loveholidays.flightsfinder.entity;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

public class Route {

    private final List<City> route;

    private int price;

    public Route(City origin){
        route = new ArrayList<>();
        price = 0;
        route.add(origin);
    }

    public Route(City origin,City destination, int price){
       this(origin);
       addDestination(destination,price);
    }

    public void addDestination(City destination, int price){
        route.add(destination);
        this.price += price;
    }

    public List<City> getRoute() {
        return Collections.unmodifiableList(route);
    }

    public int getPrice() {
        return price;
    }
}
