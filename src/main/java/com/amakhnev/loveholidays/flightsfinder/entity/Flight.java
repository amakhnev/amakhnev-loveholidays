package com.amakhnev.loveholidays.flightsfinder.entity;

public class Flight {

    private final City from;
    private final City to;
    private final int price;

    public City getFrom() {
        return from;
    }

    public City getTo() {
        return to;
    }

    public int getPrice() {
        return price;
    }

    public Flight(City from, City to, int price) {
        this.from = from;
        this.to = to;
        this.price = price;
    }
}
