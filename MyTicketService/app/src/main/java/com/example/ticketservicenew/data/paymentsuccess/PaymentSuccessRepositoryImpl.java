package com.example.ticketservicenew.data.paymentsuccess;

import com.example.ticketservicenew.business.model.Seat;
import com.example.ticketservicenew.data.provider.web.Api;

import java.util.List;

public class PaymentSuccessRepositoryImpl implements PaymentSuccesRepository{
    public static final String TAG = PaymentSuccessRepositoryImpl.class.getName();
    private Api api;

    String eventId;
    List<Seat> bookedSeats;

    public PaymentSuccessRepositoryImpl(Api api) {
        this.api = api;
    }

    @Override
    public void saveBookedSeats(List<Seat> seats) {
        this.bookedSeats = seats;
    }

    @Override
    public List<Seat> getBookedSeats() {
        return bookedSeats;
    }

    @Override
    public void saveId(String eventId) {
        this.eventId = eventId;
    }

    @Override
    public String getEventId() {
        return eventId;
    }
}
