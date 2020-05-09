package com.example.ticketservicenew.business.paying;

import com.example.ticketservicenew.business.model.Seat;

import java.util.List;

public interface PayingInteractor {
    String getEventId();

    List<Seat> getBookedSeats();

    void saveId(String eventId);

    void saveBookedSeats(List<Seat> seats);

    void sellTickets();
}
