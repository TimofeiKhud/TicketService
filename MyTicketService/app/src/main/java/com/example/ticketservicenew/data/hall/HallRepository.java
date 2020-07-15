package com.example.ticketservicenew.data.hall;

import com.example.ticketservicenew.business.model.BookingInfo;
import com.example.ticketservicenew.business.model.HallStructure;
import com.example.ticketservicenew.business.model.LockedSeats;
import com.example.ticketservicenew.business.model.Price;
import com.example.ticketservicenew.business.model.Seat;
import com.example.ticketservicenew.data.dto.objectsForDto.PriceRanges;

import java.util.List;
import java.util.Map;

import io.reactivex.Completable;
import io.reactivex.Single;

public interface HallRepository {

    //return hall structure for current event
    Single<HallStructure> getHallStructure(String id);

    Completable onSeatsBooking(List<LockedSeats> seats);

    //if shopping cart already contains tickets for any event, return its id
    String getCurrentBookingEventId();

    BookingInfo getCurrentBookingInfo(String eventId);

    boolean saveBookingInfo(BookingInfo bookingInfo);

    void saveEventId(String eventId);

    List<Price> getPriceList();

    void savePriceList(List<Price> priceList);

    String getEventId();

    String getToken();
}
