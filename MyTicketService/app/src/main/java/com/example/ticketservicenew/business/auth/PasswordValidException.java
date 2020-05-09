package com.example.ticketservicenew.business.auth;

public class PasswordValidException extends RuntimeException{
    public PasswordValidException(String message) {
        super(message);
    }
}
