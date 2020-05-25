package com.example.ticketservicenew.business.hall;

public class NoSeatsToBookException extends RuntimeException{
    public NoSeatsToBookException(String message) {
        super(message);
    }
}
