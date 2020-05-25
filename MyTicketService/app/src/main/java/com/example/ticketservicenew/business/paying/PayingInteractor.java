package com.example.ticketservicenew.business.paying;

import com.example.ticketservicenew.business.model.BookingInfo;
import com.example.ticketservicenew.business.model.LockedSeats;
import com.example.ticketservicenew.business.model.Seat;

import java.util.List;

import io.reactivex.Completable;

public interface PayingInteractor {
    String getEventId();

    List<LockedSeats> getBookedSeats();

//    void saveId(String eventId);

    //void saveBookedSeats(List<Seat> seats);

    Completable sellTickets();

    BookingInfo getBookingInfo(String eventId);

    double getTotalPrice();

    void onSellSuccess();
}
