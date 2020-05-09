package com.example.ticketservicenew.business.shoppingcart;

import com.example.ticketservicenew.business.model.Seat;

import java.util.List;
import java.util.Map;

import io.reactivex.Completable;

public interface ShoppingCartInteractor {
    Completable onBookingCancel();
    void saveId(String eventId);
    void saveBookedSeats(List<Seat> seats);
    String getEventId();
    List<Seat> getBookedSeats();
}
