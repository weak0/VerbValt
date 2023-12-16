package com.example.verbvaultjava.exception;

public class UserWordAlreadyExistsException extends RuntimeException{
    public UserWordAlreadyExistsException(String message) {
        super(message);
    }
}
