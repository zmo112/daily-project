package com.HelloWorld.Daily.exception.customException;

public class NotExistMemberException extends RuntimeException {

    public NotExistMemberException(String errorMessage){
        super(errorMessage);
    }

}

