package com.example.ticketservicenew.data.shoppingcart;

import com.example.ticketservicenew.business.model.BookingInfo;
import com.example.ticketservicenew.business.model.Event;
import com.example.ticketservicenew.business.model.HallStructure;
import com.example.ticketservicenew.business.model.Price;
import com.example.ticketservicenew.business.model.Seat;

import java.util.List;

import io.reactivex.Completable;
import io.reactivex.Single;

public interface ShoppingCartRepository {

    Single<Event> getEvent();

    Single<Void> onBookingCancel();

    BookingInfo getBookingInfo();

    String getEventId();

    //double getTotalPrice();

    //int getTotalTicketsNum();



    //Single<List<Price>> getPriceList(String eventId);

//    void saveId(String eventId);
//
//    void saveBookedSeats(List<Seat> seats);
//

//
//    List<Seat> getBookedSeats();
}
