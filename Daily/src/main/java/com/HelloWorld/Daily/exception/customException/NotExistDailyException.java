package com.HelloWorld.Daily.exception.customException;

public class NotExistDailyException extends RuntimeException {

    public NotExistDailyException(String errorMessage){
        super(errorMessage);
    }

}

