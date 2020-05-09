package com.example.ticketservicenew.business.model;

public class Seat {
    String seatNum;
    String row;
    double price;

    public Seat(String seatNum, String row, double price) {
        this.seatNum = seatNum;
        this.row = row;
        this.price = price;
    }

    public Seat(String seatNum, String row) {
        this.seatNum = seatNum;
        this.row = row;
    }

    public String getSeatNum() {
        return seatNum;
    }

    public String getRow() {
        return row;
    }

    public double getPrice() {
        return price;
    }

    @Override
    public String toString() {
        return "Seat{" +
                "seatNum='" + seatNum + '\'' +
                ", row='" + row + '\'' +
                ", price=" + price +
                '}';
    }
}
