package com.example.verbvaultjava.exception;

public class InvalidUserPassword extends RuntimeException{
    public InvalidUserPassword(String message) {
        super(message);
    }
}
