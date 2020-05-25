package com.example.ticketservicenew.business.shoppingcart;

import com.example.ticketservicenew.business.model.BookingInfo;
import com.example.ticketservicenew.business.model.Event;
import com.example.ticketservicenew.business.model.Seat;

import java.util.List;
import java.util.Map;

import io.reactivex.Completable;
import io.reactivex.Single;

public interface ShoppingCartInteractor {

    Completable onBookingCancel();

    BookingInfo getBookingInfo();

    String getEventId();

    Single<Event> getEvent();
    //int getNumTicketsBooked();
    //double getTotalPrice();
//    void saveId(String eventId);
//    void saveBookedSeats(List<Seat> seats);

//    List<Seat> getBookedSeats();
}
