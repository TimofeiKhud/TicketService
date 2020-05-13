package com.example.ticketservicenew.business.hall;

import com.example.ticketservicenew.business.model.HallStructure;
import com.example.ticketservicenew.business.model.Seat;

import java.util.List;
import java.util.Map;

import io.reactivex.Completable;
import io.reactivex.Single;

public interface HallInteractor {
    Single<HallStructure> getHallStructure(String id, boolean isShort);
    Completable onSeatsBooking(List<Seat> seats);
    String getEventId();
    void onBookingSuccess(List<Seat> seats);
}
