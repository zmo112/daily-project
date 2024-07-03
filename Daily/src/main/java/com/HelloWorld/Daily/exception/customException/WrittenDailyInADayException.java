package com.HelloWorld.Daily.exception.customException;

public class WrittenDailyInADayException extends RuntimeException {

    public WrittenDailyInADayException(String errorMessage) {
        super(errorMessage);
    }
}
