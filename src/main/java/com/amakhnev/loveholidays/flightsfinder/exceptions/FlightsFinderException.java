package com.amakhnev.loveholidays.flightsfinder.exceptions;

public class FlightsFinderException extends Exception {

    private final int code;

    public FlightsFinderException(int code, String errorMessage){
        super(errorMessage);
        this.code = code;
    }

    public int getCode() {
        return code;
    }
}
