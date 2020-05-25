package com.example.ticketservicenew.business.hall;

public class MaxSeatsSelectedException extends RuntimeException{
    public MaxSeatsSelectedException(String message) {
        super(message);
    }
}
