package com.example.ticketservicenew.data.dto.objectsForDto;

import java.util.List;

public class LockedSeatsDto {
    public String row;
    public List<String> seats;

    public LockedSeatsDto(String row, List<String> seats) {
        this.row = row;
        this.seats = seats;
    }

    public LockedSeatsDto() {
    }

    public String getRow() {
        return row;
    }

    public void setRow(String row) {
        this.row = row;
    }

    public List<String> getSeats() {
        return seats;
    }

    public void setSeats(List<String> seats) {
        this.seats = seats;
    }


}
