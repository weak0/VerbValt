package com.example.verbvaultjava.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class ExceptionControllerAdvice {
    private static final Map<Class<? extends Exception>, HttpStatus> EXCEPTION_STATUS = new HashMap<>();

    static {
        EXCEPTION_STATUS.put(UserRoleException.class, HttpStatus.BAD_REQUEST);
        EXCEPTION_STATUS.put(InvalidUserPassword.class,HttpStatus.CONFLICT);
        EXCEPTION_STATUS.put(UserNotFoundException.class,HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(Exception.class)
    ResponseEntity<String> handleException(Exception e) {
        HttpStatus status = EXCEPTION_STATUS.getOrDefault(e.getClass(), HttpStatus.INTERNAL_SERVER_ERROR);
        return ResponseEntity.status(status).body(e.getMessage());
    }
}
