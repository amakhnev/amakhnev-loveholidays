package com.amakhnev.loveholidays.flightsfinder.repository;

import com.amakhnev.loveholidays.flightsfinder.entity.City;
import com.amakhnev.loveholidays.flightsfinder.exceptions.FlightsFinderException;
import com.amakhnev.loveholidays.flightsfinder.exceptions.FlightsFinderExceptionEnum;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class CsvFlightsRepository implements FlightsRepository {


    private final String fileName;
    private boolean loaded = false;
    private final List<City> cities;

    private int[][] prices;


    public CsvFlightsRepository(String fileName) {
        this.fileName = fileName;
        cities = new ArrayList<>();
    }


    @Override
    public List<City> getCities() throws FlightsFinderException {
        if (!loaded) {
            loadData();
        }
        return cities;
    }

    @Override
    public Optional<City> getCity(String name) throws FlightsFinderException {
        if (!loaded) {
            loadData();
        }
        City city = new City(name);
        if (cities.contains(city)){
            return Optional.of(city);
        }
        return Optional.empty();
    }

    @Override
    public List<City> getDestinations(City origin) throws FlightsFinderException {
        if (!loaded) {
            loadData();
        }
        return cities.stream()
                    .skip(cities.indexOf(origin)+1)
                    .collect(Collectors.toList());
    }

    @Override
    public int getPrice(City origin, City destination) throws FlightsFinderException {
        if (!loaded) {
            loadData();
        }

        if (cities.indexOf(origin) >= cities.indexOf(destination)){
            throw new FlightsFinderException(FlightsFinderExceptionEnum.REPO_WRONG_ARGS);
        }
        return prices[cities.indexOf(origin)][cities.indexOf(destination)];
    }

    private void loadData() throws FlightsFinderException {


        ClassLoader classLoader = getClass().getClassLoader();
        InputStream inputStream = classLoader.getResourceAsStream(fileName);

        if (inputStream == null) {
            throw new FlightsFinderException(FlightsFinderExceptionEnum.REPO_IO);
        }

        List<String> lines = new BufferedReader(new InputStreamReader(inputStream))
                .lines()
                .collect(Collectors.toList());

        if (lines.size()>0){
            for (String cityString : lines.get(0).split(",")) {
                cities.add(new City(cityString.trim()));
            }
        } else{
            throw new FlightsFinderException(FlightsFinderExceptionEnum.REPO_NO_HEADER);
        }

        if (lines.size() != cities.size()+1){
            throw new FlightsFinderException(FlightsFinderExceptionEnum.REPO_WRONG_LINES);
        }

        this.prices = new int[cities.size()][cities.size()];

        // now process flights
        for (int i=1; i< lines.size();i++){
            String [] flightsStr = lines.get(i).split(",");
            if (flightsStr.length != cities.size()){
                throw new FlightsFinderException(FlightsFinderExceptionEnum.REPO_WRONG_LINES);
            }
            for (int j=0; j< flightsStr.length; j++){
                try {
                    this.prices[i-1][j] = Integer.parseInt(flightsStr[j].trim());
                } catch (NumberFormatException e) {
                    throw new FlightsFinderException(FlightsFinderExceptionEnum.REPO_WRONG_LINES);
                }
            }
        }

        loaded = true;
    }
}
