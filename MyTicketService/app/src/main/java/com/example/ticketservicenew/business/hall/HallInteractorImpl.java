package com.example.ticketservicenew.business.hall;

import com.example.ticketservicenew.business.model.HallStructure;
import com.example.ticketservicenew.business.model.Seat;
import com.example.ticketservicenew.data.hall.HallRepository;


import java.util.List;

import io.reactivex.Completable;
import io.reactivex.Single;

public class HallInteractorImpl implements HallInteractor{
    private static final String TAG = HallInteractorImpl.class.getName();
    HallRepository repository;

    public HallInteractorImpl(HallRepository repository) {
        this.repository = repository;
    }

    @Override
    public Single<HallStructure> getHallStructure(String id, boolean isShort) {
        return repository.getHallStructure(id, isShort);
    }

    @Override
    public Completable onSeatsBooking(List<Seat> seats) {
        return repository.onSeatsBooking(seats);
    }

    @Override
    public String getEventId() {
        return repository.getEventId();
    }

    @Override
    public void onBookingSuccess(List<Seat> seats) {

    }
}
