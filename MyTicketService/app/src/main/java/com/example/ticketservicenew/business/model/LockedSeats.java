package com.example.ticketservicenew.business.model;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

/**
 * Contains number of the row, locked seats in the row and price for one seat
 */
public class LockedSeats {
    private String row;
    private List<String> seats;

    public LockedSeats(String row, List<String> seats) {
        this.row = row;
        this.seats = seats;
    }

    public String getRow() {
        return row;
    }

    public List<String> getSeats() {
        return seats;
    }

    public void addSeat(String seat) {
        seats.add(seat);
    }

}
