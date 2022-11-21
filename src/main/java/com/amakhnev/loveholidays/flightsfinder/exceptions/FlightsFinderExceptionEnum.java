package com.amakhnev.loveholidays.flightsfinder.exceptions;

public enum FlightsFinderExceptionEnum {
    APP_WRONG_ARGS(100,"Wrong number of arguments"),
    APP_NO_ROUTES(101,"No routes are returned"),
    SERVICE_GENERIC(1,"Generic error"),

    REPO_IO(201,"Unable to access flight repository"),
    REPO_NO_HEADER(202,"No header found"),
    REPO_WRONG_LINES(203,"Wrong flight lines found"),

    REPO_WRONG_ARGS(204,"Invalid arguments");


    final String message;
    final int code;

    public String getMessage() {
        return message;
    }

    public int getCode() {
        return code;
    }

    FlightsFinderExceptionEnum(int code, String message){
        this.message = message;
        this.code = code;
    }

}
