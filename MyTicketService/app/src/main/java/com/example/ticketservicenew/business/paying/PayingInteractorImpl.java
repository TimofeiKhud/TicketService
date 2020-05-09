package com.example.ticketservicenew.business.paying;

import com.example.ticketservicenew.business.model.Seat;
import com.example.ticketservicenew.data.paying.PayingRepository;

import java.util.List;

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
    public List<Seat> getBookedSeats() {
        return repository.getBookedSeats();
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
    public void sellTickets() {
        repository.sellTickets();
    }
}
