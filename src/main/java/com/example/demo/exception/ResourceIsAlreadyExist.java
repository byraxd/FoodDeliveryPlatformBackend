package com.example.demo.exception;

public class ResourceIsAlreadyExist extends RuntimeException {
    public ResourceIsAlreadyExist(String message) {
        super(message);
    }
}
