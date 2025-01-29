package com.encora.ToDosBackend.service;

public class ValidationException extends RuntimeException{
    public ValidationException(String message){
        super(message);
    }
}
