package com.example.ticketservicenew.business.paymentsuccess;

import com.example.ticketservicenew.business.model.BookingInfo;
import com.example.ticketservicenew.business.model.LockedSeats;
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
    public List<LockedSeats> getSoldSeats() {
        return repository.getSoldSeats();
    }

    @Override
    public BookingInfo getPaymentInfo(String eventId) {
        return repository.getPaymentInfo(eventId);
    }

    @Override
    public void clearBookingInfo() {
        repository.clearBookingInfo();
    }
}
