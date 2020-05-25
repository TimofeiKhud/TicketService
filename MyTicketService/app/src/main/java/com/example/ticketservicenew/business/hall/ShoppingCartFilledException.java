package com.example.ticketservicenew.business.hall;

public class ShoppingCartFilledException extends RuntimeException{
    public ShoppingCartFilledException(String message) {
        super(message);
    }
}
