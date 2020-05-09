package com.example.ticketservicenew.data.paymentsuccess;

import com.example.ticketservicenew.business.model.Seat;

import java.util.List;

public interface PaymentSuccesRepository {
    String getEventId();

    List<Seat> getBookedSeats();

    void saveId(String eventId);

    void saveBookedSeats(List<Seat> seats);
}
