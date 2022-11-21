package com.amakhnev.loveholidays.flightsfinder.repository;

import com.amakhnev.loveholidays.flightsfinder.entity.City;
import com.amakhnev.loveholidays.flightsfinder.exceptions.FlightsFinderException;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

public class CsvFlightsRepository implements FlightsRepository{

    public static int ERROR_IO_CODE = 1000;
    public static String ERROR_IO_MESSAGE = "unable to open flights configuration file";


    private final String fileName;
    private boolean loaded = false;
    private final List<City> cities;

    public CsvFlightsRepository(String fileName){
        this.fileName = fileName;
        cities = new ArrayList<>();
    }


    @Override
    public List<City> getCities() throws FlightsFinderException  {
        if (!loaded){
            loadData();
        }
        return cities;
    }

    @Override
    public City getCity(String name) throws FlightsFinderException {
        if (!loaded){
            loadData();
        }
        return null;
    }

    private void loadData() throws FlightsFinderException {
        Stream<String> lines = null;
        try {
            ClassLoader classLoader = getClass().getClassLoader();
            InputStream inputStream = classLoader.getResourceAsStream(fileName);

            lines = new BufferedReader(new InputStreamReader(inputStream)).lines();
        } catch (Exception e) {
            throw new FlightsFinderException(ERROR_IO_CODE,ERROR_IO_MESSAGE);
        }
        lines.forEach(s -> System.out.println(s));
        loaded = true;
    }
}
