package com.example.users_module.httpRequester;

public class ServiceException extends RuntimeException{
    public ServiceException(String message) {
        super(message);
    }
}
