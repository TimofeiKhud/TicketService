package com.example.ticketservicenew.business.model;

import java.util.List;

public class Price {
    public String color;
    public double price;
    public List<String> rows;

    public Price(String color, double price, List<String> rows) {
        this.color = color;
        this.price = price;
        this.rows = rows;
    }

    public String getColor() {
        return color;
    }

    public double getPrice() {
        return price;
    }

    public List<String> getRows() {
        return rows;
    }
}
