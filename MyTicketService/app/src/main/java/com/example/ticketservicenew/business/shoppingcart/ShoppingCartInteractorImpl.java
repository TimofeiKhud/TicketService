package com.example.ticketservicenew.business.shoppingcart;

import com.example.ticketservicenew.business.model.Seat;
import com.example.ticketservicenew.data.shoppingcart.ShoppingCartRepository;

import java.util.List;

import io.reactivex.Completable;

public class ShoppingCartInteractorImpl implements ShoppingCartInteractor{
    private static final String TAG = ShoppingCartInteractorImpl.class.getName();
ShoppingCartRepository repository;

    public ShoppingCartInteractorImpl(ShoppingCartRepository repository) {
        this.repository = repository;
    }

    @Override
    public Completable onBookingCancel() {
        return Completable.fromSingle(repository.onBookingCancel());
    }

    @Override
    public void saveId(String eventId) {
        repository.saveId(eventId);
    }

    @Override
    public void saveBookedSeats(List<Seat> seats) {
        repository.saveBookedSeats(seats);
    }

    @Override
    public String getEventId() {
        return repository.getId();
    }

    @Override
    public List<Seat> getBookedSeats() {
        return repository.getBookedSeats();
    }
}
