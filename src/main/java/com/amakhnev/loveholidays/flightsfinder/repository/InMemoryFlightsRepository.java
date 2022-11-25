package com.amakhnev.loveholidays.flightsfinder.repository;

import com.amakhnev.loveholidays.flightsfinder.entity.City;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Random;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class InMemoryFlightsRepository implements FlightsRepository{
    private final List<City> cities;

    private final int[][] prices;


    public InMemoryFlightsRepository(int size){
        cities = IntStream.range(0,size).mapToObj((i) -> new City("City "+(i+1))).collect(Collectors.toList());

        prices = new int[size][size];
        Random random = new Random();
        for (int i = 0; i <size; i++) {
            for (int j = 0; j < size; j++) {
                if (i!=j){
                    prices[i][j] = random.nextInt(100);
                }
            }
        }
    }

    @Override
    public List<City> getCities() {
        return cities;
    }

    @Override
    public Optional<City> getCity(String name) {
        City city = new City(name);
        if (cities.contains(city)){
            return Optional.of(city);
        }
        return Optional.empty();
    }

    @Override
    public List<City> getDestinations(City origin) {
        int idx = cities.indexOf(origin);

        return idx==-1?
                new ArrayList<>():
                IntStream.range(0,cities.size())
                        .filter(i-> idx!=i && prices[idx][i]>0)
                        .mapToObj(cities::get)
                        .collect(Collectors.toList());
    }

    @Override
    public Optional<Integer> getPrice(City origin, City destination) {
        return prices[cities.indexOf(origin)][cities.indexOf(destination)]>0?Optional.of(prices[cities.indexOf(origin)][cities.indexOf(destination)]):Optional.empty();
    }
}
