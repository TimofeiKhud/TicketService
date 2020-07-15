package com.example.ticketservicenew.business.hall;

import com.example.ticketservicenew.business.model.BookingInfo;
import com.example.ticketservicenew.business.model.HallStructure;
import com.example.ticketservicenew.business.model.LockedSeats;
import com.example.ticketservicenew.business.model.Price;
import com.example.ticketservicenew.business.model.Seat;

import java.util.List;
import java.util.Map;

import io.reactivex.Completable;
import io.reactivex.Single;

public interface HallInteractor {
    Single<HallStructure> getHallStructure(String id);

    Completable onSeatsBooking(List<Seat> seats);

    String getEventId();

    BookingInfo getCurrentBookingInfo(String eventId);

    void onBookingSuccess();

    void saveId(String eventId);

    void savePriceList(List<Price> priceList);

    void onSeatSelected(int selectedSeatsNum);

}
