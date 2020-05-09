package com.example.ticketservicenew.data.paying;


import android.util.Log;

import com.example.ticketservicenew.business.model.Seat;
import com.example.ticketservicenew.data.dto.EventBookingDto;
import com.example.ticketservicenew.data.provider.web.Api;

import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import io.reactivex.Single;
import retrofit2.Response;

public class PayingRepositoryImpl implements PayingRepository{
    public static final String TAG = PayingRepositoryImpl.class.getName();
    private Api api;

    String eventId;
    List<Seat> bookedSeats;

    public PayingRepositoryImpl(Api api) {
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

    @Override
    public void sellTickets() {

    }

    private EventBookingDto mapSeatModelToDto(List<Seat> seats){
        Map<String, List<String>> map = new LinkedHashMap<>();
        for(Seat seat : seats){
            Log.d(TAG, "mapper: " + seat.getSeatNum());
            List<String> seatList = map.containsKey(seat.getRow()) && map.get(seat.getRow()) != null ?
                    map.get(seat.getRow()) : new ArrayList<>();
            seatList.add(seat.getSeatNum());
            map.put(seat.getRow(), seatList);
        }
        List<Map<String, List<String>>> lockedSeats = new ArrayList<>();
        lockedSeats.add(map);
        return new EventBookingDto(eventId, lockedSeats);
    }

    private Single<Void> onSellTicketsSuccess(Response<Void> response) throws IOException {
        if (response.isSuccessful()){
            Log.d(TAG, "onSellTickets success: " + response.body());
            return Single.just(response.body());
        }else {
            Log.d(TAG, "onSellTickets error: " + response.errorBody().string());
            throw new RuntimeException("Server error! Call to support");
        }
    }
}
