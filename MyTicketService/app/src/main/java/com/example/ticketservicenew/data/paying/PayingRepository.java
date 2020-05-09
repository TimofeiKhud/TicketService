package com.example.ticketservicenew.data.paying;

import com.example.ticketservicenew.business.model.Seat;

import java.util.List;

public interface PayingRepository {
    void saveBookedSeats(List<Seat> seats);

    List<Seat> getBookedSeats();

    void saveId(String eventId);

    String getEventId();

    void sellTickets();
}
