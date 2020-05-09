package com.example.ticketservicenew.business.hall;

import android.util.Log;

import com.example.ticketservicenew.business.model.HallStructure;
import com.example.ticketservicenew.business.model.Seat;
import com.example.ticketservicenew.data.hall.HallRepository;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
    public Completable onSeatsBooked(List<Seat> seats) {
        Map<String, List<String>> bookedSeats = new HashMap<>();
        for(Seat seat : seats){
            Log.d(TAG, "hallInteractor, seat to book:" + seat.toString());
            if(!bookedSeats.isEmpty() && bookedSeats.containsKey(seat.getRow())){
                List<String> seatList = bookedSeats.get(seat.getRow());
                if(!seatList.contains(seat.getSeatNum())) {
                    seatList.add(seat.getSeatNum());
                }
                bookedSeats.put(seat.getRow(), seatList);
            }else{
                List<String> list = new ArrayList<>();
                list.add(seat.getSeatNum());
                bookedSeats.put(seat.getRow(), list);
            }
        }
        return repository.onSeatsBooked(bookedSeats);
    }

    @Override
    public String getEventId() {
        return repository.getEventId();
    }
}
