package com.example.ticketservicenew.data.hall;

import com.example.ticketservicenew.business.model.HallStructure;
import com.example.ticketservicenew.business.model.Seat;

import java.util.List;
import java.util.Map;

import io.reactivex.Completable;
import io.reactivex.Single;

public interface HallRepository {
    Single<HallStructure> getHallStructure(String id, boolean isShort);
    Completable onSeatsBooked(Map<String, List<String>> seats);
    //Single<Void> onSeatsBooked(Map<String, List<String>> seats);
    String getEventId();
}
