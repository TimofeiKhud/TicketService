package com.example.ticketservicenew.business.hall;

public class UnregisteredUserException extends RuntimeException{
    public UnregisteredUserException(String message) {
        super(message);
    }
}
