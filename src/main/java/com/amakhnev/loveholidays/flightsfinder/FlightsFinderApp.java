package com.amakhnev.loveholidays.flightsfinder;

import com.amakhnev.loveholidays.flightsfinder.entity.City;
import com.amakhnev.loveholidays.flightsfinder.entity.Route;
import com.amakhnev.loveholidays.flightsfinder.exceptions.FlightsFinderException;
import com.amakhnev.loveholidays.flightsfinder.repository.CsvFlightsRepository;
import com.amakhnev.loveholidays.flightsfinder.repository.FlightsRepository;
import com.amakhnev.loveholidays.flightsfinder.service.FlightsFinderService;

import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.List;

public class FlightsFinderApp {

    public static void main(String[] args) throws IOException {
        FlightsFinderApp app = new FlightsFinderApp();

        app.process(args, new OutputStreamWriter(System.out));
    }

    public static final String ERROR_TEXT_TEMPLATE = "Error happened, code: %s, message: %s.";



    protected void process(String[] args, Writer output) throws IOException {
        FlightsRepository flightsRepository = new CsvFlightsRepository("flights.csv");
        FlightsFinderService service = new FlightsFinderService(flightsRepository);
        process(service, args, output);
    }

    protected void process(FlightsFinderService service , String[] args, Writer output) throws IOException {

        if (args.length != 2) {
            output.write(String.format(ERROR_TEXT_TEMPLATE, 100, "Wrong number of arguments"));
            output.flush();
            return;
        }



        try {

            List<Route> routes = service.getRoutes();

            if (routes == null || routes.size()==0){
                output.write(String.format(ERROR_TEXT_TEMPLATE, 101, "No routes are returned"));
                output.flush();
                return;
            }

            for (Route r : routes) {

            }

        } catch (FlightsFinderException e) {
            output.write(String.format(ERROR_TEXT_TEMPLATE, e.getCode(), e.getMessage()));
            output.flush();
        }


    }



}