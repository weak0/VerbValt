package com.example.verbvaultjava.exception;

public class CourseUserAlreadyExistsException extends RuntimeException {
    public CourseUserAlreadyExistsException(String message) {
        super(message);
    }
}
