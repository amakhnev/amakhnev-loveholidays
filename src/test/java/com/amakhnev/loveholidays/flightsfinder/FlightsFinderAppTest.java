package com.amakhnev.loveholidays.flightsfinder;

import com.amakhnev.loveholidays.flightsfinder.entity.City;
import com.amakhnev.loveholidays.flightsfinder.entity.Route;
import com.amakhnev.loveholidays.flightsfinder.exceptions.FlightsFinderException;
import com.amakhnev.loveholidays.flightsfinder.exceptions.FlightsFinderExceptionEnum;
import com.amakhnev.loveholidays.flightsfinder.repository.FlightsRepository;
import com.amakhnev.loveholidays.flightsfinder.service.FlightsFinderService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.io.StringWriter;
import java.io.Writer;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Optional;

import static com.amakhnev.loveholidays.flightsfinder.FlightsFinderApp.ERROR_TEXT_TEMPLATE;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;

class FlightsFinderAppTest {

    private FlightsFinderApp app;


    @BeforeEach
    public void setUp() {

        app = new FlightsFinderApp();

    }

    @Test
    public void whenWrongNumberOfArgumentsPassed_thenExceptionShouldBeOutput() throws Exception {
        Writer stringWriter = new StringWriter();
        app.process(new String[]{}, stringWriter);
        assertEquals(String.format(ERROR_TEXT_TEMPLATE,
                        FlightsFinderExceptionEnum.APP_WRONG_ARGS.getCode(),
                        FlightsFinderExceptionEnum.APP_WRONG_ARGS.getMessage())
                , stringWriter.toString());

        stringWriter = new StringWriter();
        app.process(new String[]{"city 1"}, stringWriter);
        assertEquals(String.format(ERROR_TEXT_TEMPLATE,
                        FlightsFinderExceptionEnum.APP_WRONG_ARGS.getCode(),
                        FlightsFinderExceptionEnum.APP_WRONG_ARGS.getMessage())
                , stringWriter.toString());

        stringWriter = new StringWriter();
        app.process(new String[]{"city 1", "city 2", "city 3"}, stringWriter);
        assertEquals(String.format(ERROR_TEXT_TEMPLATE,
                        FlightsFinderExceptionEnum.APP_WRONG_ARGS.getCode(),
                        FlightsFinderExceptionEnum.APP_WRONG_ARGS.getMessage())
                , stringWriter.toString());

    }


    @Test
    public void whenNoRoutesReturned_thenRightCodeAndMessageShouldBeOut() throws Exception {
        City city1 = new City("City 1");
        City city2 = new City("City 2");

        FlightsRepository repository = mock(FlightsRepository.class);
        Mockito.doReturn(Optional.of(city1)).when(repository).getCity("City 1");
        Mockito.doReturn(Optional.of(city2)).when(repository).getCity("City 2");

        // return empty list
        FlightsFinderService service = mock(FlightsFinderService.class);
        Mockito.doReturn(new ArrayList<>()).when(service).getRoutes(city1, city2);

        Writer stringWriter = new StringWriter();
        app.process(repository, service, new String[]{"City 1", "City 2"}, stringWriter);
        assertEquals(String.format(ERROR_TEXT_TEMPLATE,
                        FlightsFinderExceptionEnum.APP_NO_ROUTES.getCode(),
                        FlightsFinderExceptionEnum.APP_NO_ROUTES.getMessage())
                , stringWriter.toString());

        // return null
        Mockito.doReturn(null).when(service).getRoutes(city1, city2);
        stringWriter = new StringWriter();
        app.process(repository, service, new String[]{"City 1", "City 2"}, stringWriter);
        assertEquals(String.format(ERROR_TEXT_TEMPLATE,
                        FlightsFinderExceptionEnum.APP_NO_ROUTES.getCode(),
                        FlightsFinderExceptionEnum.APP_NO_ROUTES.getMessage())
                , stringWriter.toString());
    }


    @Test
    public void whenServiceReturnsError_thenRightCodeAndMessageShouldBeOut() throws Exception {
        City city1 = new City("City 1");
        City city2 = new City("City 2");

        FlightsFinderService service = mock(FlightsFinderService.class);
        Mockito.doThrow(new FlightsFinderException(FlightsFinderExceptionEnum.SERVICE_GENERIC))
                .when(service).getRoutes(city1, city2);

        FlightsRepository repository = mock(FlightsRepository.class);
        Mockito.doReturn(Optional.of(city1)).when(repository).getCity("City 1");
        Mockito.doReturn(Optional.of(city2)).when(repository).getCity("City 2");

        Writer stringWriter = new StringWriter();
        app.process(repository, service, new String[]{"City 1", "City 2"}, stringWriter);
        assertEquals(String.format(ERROR_TEXT_TEMPLATE,
                        FlightsFinderExceptionEnum.SERVICE_GENERIC.getCode(),
                        FlightsFinderExceptionEnum.SERVICE_GENERIC.getMessage())
                , stringWriter.toString());

    }

    @Test
    public void whenIncorrectOriginInput_thenErrorShouldBeRaised() throws Exception {


        FlightsRepository repository = mock(FlightsRepository.class);
        Mockito.doReturn(Optional.empty()).when(repository).getCity("City 1");

        FlightsFinderService service = mock(FlightsFinderService.class);


        Writer stringWriter = new StringWriter();
        app.process(repository, service, new String[]{"City 1", "City 2"}, stringWriter);
        assertEquals(String.format(ERROR_TEXT_TEMPLATE,
                        FlightsFinderExceptionEnum.SERVICE_WRONG_ARGS.getCode(),
                        FlightsFinderExceptionEnum.SERVICE_WRONG_ARGS.getMessage())
                , stringWriter.toString());

    }

    @Test
    public void whenRouteIsNotEmpty_thenItShouldOutputCorrectly() throws Exception {
        City city1 = new City("City 1");
        City city2 = new City("City 2");
        City city3 = new City("City 3");


        FlightsRepository repository = mock(FlightsRepository.class);
        Mockito.doReturn(Optional.of(city1)).when(repository).getCity("City 1");
        Mockito.doReturn(Optional.of(city2)).when(repository).getCity("City 2");
        Mockito.doReturn(Optional.of(city3)).when(repository).getCity("City 3");


        FlightsFinderService service = mock(FlightsFinderService.class);
        Route route1 = new Route(city1);
        route1.addDestination(city3,100);

        Route route2 = new Route(city1);
        route2.addDestination(city2,30);
        route2.addDestination(city3,40);


        Mockito.doReturn(Arrays.asList(route1,route2)).when(service).getRoutes(city1, city3);

        Writer stringWriter = new StringWriter();
        app.process(repository, service, new String[]{"City 1", "City 3"}, stringWriter);

        String expectedOutput = "City 1 -> City 3: 100" +
                System.lineSeparator() +
                "City 1 -> City 2 -> City 3: 70" +
                System.lineSeparator();

        assertEquals(expectedOutput
                , stringWriter.toString());

    }

}