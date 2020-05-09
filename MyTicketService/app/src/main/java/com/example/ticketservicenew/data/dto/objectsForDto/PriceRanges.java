package com.example.ticketservicenew.data.dto.objectsForDto;

public class PriceRanges {
    LockedSeatsDto seats;
    double price;
    String color;

    public PriceRanges() {
    }

    public PriceRanges(LockedSeatsDto seats, double price, String color) {
        this.seats = seats;
        this.price = price;
        this.color = color;
    }

    public LockedSeatsDto getSeats() {
        return seats;
    }

    public double getPrice() {
        return price;
    }

    public String getColor() {
        return color;
    }

    @Override
    public String toString() {
        return "PriceRanges{" +
                "seats=" + seats +
                ", price=" + price +
                ", color='" + color + '\'' +
                '}';
    }
}
