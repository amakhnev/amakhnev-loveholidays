package com.amakhnev.loveholidays.flightsfinder.exceptions;

public class FlightsFinderException extends Exception {

    private final int code;

    public FlightsFinderException(FlightsFinderExceptionEnum exceptionEnum){
        super(exceptionEnum.getMessage());
        this.code = exceptionEnum.getCode();
    }

    public int getCode() {
        return code;
    }


}


