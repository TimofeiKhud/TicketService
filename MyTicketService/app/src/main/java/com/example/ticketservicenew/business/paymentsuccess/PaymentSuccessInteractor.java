package com.example.ticketservicenew.business.paymentsuccess;

import com.example.ticketservicenew.business.model.Seat;

import java.util.List;

public interface PaymentSuccessInteractor {

    String getEventId();

    List<Seat> getBookedSeats();

    void saveId(String eventId);

    void saveBookedSeats(List<Seat> seats);

}
