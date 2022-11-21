package com.amakhnev.loveholidays.flightsfinder;

import com.amakhnev.loveholidays.flightsfinder.exceptions.FlightsFinderException;
import com.amakhnev.loveholidays.flightsfinder.exceptions.FlightsFinderExceptionEnum;
import com.amakhnev.loveholidays.flightsfinder.service.FlightsFinderService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.io.StringWriter;
import java.io.Writer;
import java.util.ArrayList;

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
        // return empty list
        FlightsFinderService service = mock(FlightsFinderService.class);
        Mockito.doReturn(new ArrayList<>()).when(service).getRoutes("City 1", "City 2");

        Writer stringWriter = new StringWriter();
        app.process(new String[]{"City 1", "City 2"}, stringWriter);
        assertEquals(String.format(ERROR_TEXT_TEMPLATE,
                        FlightsFinderExceptionEnum.APP_NO_ROUTES.getCode(),
                        FlightsFinderExceptionEnum.APP_NO_ROUTES.getMessage())
                , stringWriter.toString());

        // return null
        Mockito.doReturn(null).when(service).getRoutes("City 1", "City 2");
        stringWriter = new StringWriter();
        app.process(new String[]{"City 1", "City 2"}, stringWriter);
        assertEquals(String.format(ERROR_TEXT_TEMPLATE,
                        FlightsFinderExceptionEnum.APP_NO_ROUTES.getCode(),
                        FlightsFinderExceptionEnum.APP_NO_ROUTES.getMessage())
                , stringWriter.toString());
    }


    @Test
    public void whenServiceReturnsError_thenRightCodeAndMessageShouldBeOut() throws Exception {
        FlightsFinderService service = mock(FlightsFinderService.class);
        Mockito.doThrow(new FlightsFinderException(FlightsFinderExceptionEnum.SERVICE_GENERIC))
                .when(service).getRoutes("City 1", "City 2");

        Writer stringWriter = new StringWriter();
        app.process(service, new String[]{"City 1", "City 2"}, stringWriter);
        assertEquals(String.format(ERROR_TEXT_TEMPLATE,
                        FlightsFinderExceptionEnum.SERVICE_GENERIC.getCode(),
                        FlightsFinderExceptionEnum.SERVICE_GENERIC.getMessage())
                , stringWriter.toString());

    }
}