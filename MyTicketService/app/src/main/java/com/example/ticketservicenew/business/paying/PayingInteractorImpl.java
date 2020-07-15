package com.example.ticketservicenew.business.paying;

import com.example.ticketservicenew.business.model.BookingInfo;
import com.example.ticketservicenew.business.model.LockedSeats;
import com.example.ticketservicenew.business.model.Seat;
import com.example.ticketservicenew.data.paying.PayingRepository;

import java.util.List;

import io.reactivex.Completable;

public class PayingInteractorImpl implements PayingInteractor {
    private static final String TAG = PayingInteractorImpl.class.getName();
    PayingRepository repository;

    public PayingInteractorImpl(PayingRepository repository) {
        this.repository = repository;
    }

    @Override
    public String getEventId() {
        return repository.getEventId();
    }

    @Override
    public List<LockedSeats> getBookedSeats() {
        return repository.getBookedSeats();
    }

    @Override
    public Completable sellTickets() {
        return repository.sellTickets();
    }

    @Override
    public BookingInfo getBookingInfo(String eventId) {
        return repository.getBookingInfo(eventId);
    }

    @Override
    public double getTotalPrice() {
        return repository.getTotalPrice();
    }

    @Override
    public void onSellSuccess() {
        //TODO
    }
}
