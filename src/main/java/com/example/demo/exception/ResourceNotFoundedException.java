package com.example.demo.exception;

public class ResourceNotFoundedException extends RuntimeException {

    public ResourceNotFoundedException(String message) {
        super(message);
    }

}
