package com.example.ticketservicenew.data.paying;

import com.example.ticketservicenew.business.model.BookingInfo;
import com.example.ticketservicenew.business.model.LockedSeats;
import com.example.ticketservicenew.business.model.Seat;

import java.util.List;

import io.reactivex.Completable;

public interface PayingRepository {

    Completable sellTickets();

    List<LockedSeats> getBookedSeats();

    String getEventId();

    BookingInfo getBookingInfo(String eventId);

    double getTotalPrice();
}
