package com.example.ticketservicenew.business.model;

public class Seat {
    String row;
    String seatNum;
    double price;

    public Seat(String row, String seatNum, double price) {
        this.row = row;
        this.seatNum = seatNum;
        this.price = price;
    }

    public Seat(String row, String seatNum) {
        this.row = row;
        this.seatNum = seatNum;
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
                "row='" + row + '\'' +
                ", seatNum='" + seatNum + '\'' +
                ", price=" + price +
                '}';
    }
}
