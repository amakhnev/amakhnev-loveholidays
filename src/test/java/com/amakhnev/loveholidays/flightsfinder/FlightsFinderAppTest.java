package com.amakhnev.loveholidays.flightsfinder;

import com.amakhnev.loveholidays.flightsfinder.exceptions.FlightsFinderException;
import com.amakhnev.loveholidays.flightsfinder.repository.CsvFlightsRepository;
import com.amakhnev.loveholidays.flightsfinder.repository.FlightsRepository;
import com.amakhnev.loveholidays.flightsfinder.service.FlightsFinderService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;

import java.io.IOException;
import java.io.StringWriter;
import java.io.Writer;
import java.util.ArrayList;

import static com.amakhnev.loveholidays.flightsfinder.FlightsFinderApp.ERROR_TEXT_TEMPLATE;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;

class FlightsFinderAppTest {

    private FlightsFinderApp app;



    @BeforeEach
    public void setUp(){

        app = new FlightsFinderApp();

    }

    @Test
    public void whenWrongNumberOfArgumentsPassed_thenExceptionShouldBeOutput() throws Exception {
        Writer stringWriter = new StringWriter();
        app.process(new String []{},stringWriter);
        assertEquals(String.format(ERROR_TEXT_TEMPLATE,100,"Wrong number of arguments"),stringWriter.toString());

        stringWriter = new StringWriter();
        app.process(new String []{"city 1"},stringWriter);
        assertEquals(String.format(ERROR_TEXT_TEMPLATE,100,"Wrong number of arguments"),stringWriter.toString());

        stringWriter = new StringWriter();
        app.process(new String []{"city 1","city 2","city 3"},stringWriter);
        assertEquals(String.format(ERROR_TEXT_TEMPLATE,100,"Wrong number of arguments"),stringWriter.toString());

    }



    @Test
    public void whenNoRoutesReturned_thenRightCodeAndMessageShouldBeOut() throws Exception{
        // return empty list
        FlightsFinderService service = mock(FlightsFinderService.class);
        Mockito.doReturn(new ArrayList<>()).when(service).getRoutes();

        Writer stringWriter = new StringWriter();
        app.process(new String [] {"City 1","City 2"},stringWriter);
        assertEquals(String.format(ERROR_TEXT_TEMPLATE, 101,"No routes are returned"),stringWriter.toString());

        // return null
        Mockito.doReturn(null).when(service).getRoutes();
        stringWriter = new StringWriter();
        app.process(new String [] {"City 1","City 2"},stringWriter);
        assertEquals(String.format(ERROR_TEXT_TEMPLATE, 101,"No routes are returned"),stringWriter.toString());
    }


    @Test
    public void whenServiceReturnsError_thenRightCodeAndMessageShouldBeOut() throws Exception{
        FlightsFinderService service = mock(FlightsFinderService.class);
        Mockito.doThrow(new FlightsFinderException(1,"Some message")).when(service).getRoutes();

        Writer stringWriter = new StringWriter();
        app.process(service,new String [] {"City 1","City 2"},stringWriter);
        assertEquals(String.format(ERROR_TEXT_TEMPLATE, 1,"Some message"),stringWriter.toString());

    }
}