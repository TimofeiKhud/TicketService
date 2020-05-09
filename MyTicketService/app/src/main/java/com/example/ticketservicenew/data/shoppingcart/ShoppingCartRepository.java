package com.example.ticketservicenew.data.shoppingcart;

import com.example.ticketservicenew.business.model.Seat;

import java.util.List;

import io.reactivex.Completable;
import io.reactivex.Single;

public interface ShoppingCartRepository {

    /*Completable*/Single<Void> onBookingCancel();

    void saveId(String eventId);

    void saveBookedSeats(List<Seat> seats);

    String getId();

    List<Seat> getBookedSeats();
}
