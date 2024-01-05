package com.eduardo.AdoptAPetAPI.exceptions;

public class UserAlreadyRegisteredException extends RuntimeException{
    public UserAlreadyRegisteredException(String message){
        super(message);
    }
}
