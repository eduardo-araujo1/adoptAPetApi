package com.eduardo.AdoptAPetAPI.exceptions;

public class EmailNotFoundException extends RuntimeException {
    public EmailNotFoundException(String message){
        super(message);
    }
}
