package com.example.ticketservicenew.business.paymentsuccess;

import com.example.ticketservicenew.business.model.Seat;
import com.example.ticketservicenew.data.paymentsuccess.PaymentSuccesRepository;

import java.util.List;

public class PaymentSuccessInteractorImpl implements PaymentSuccessInteractor{
    private static final String TAG = PaymentSuccessInteractor.class.getName();
    PaymentSuccesRepository repository;

    public PaymentSuccessInteractorImpl(PaymentSuccesRepository repository) {
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
}
