package com.example.ticketservicenew.data.paying;


import android.util.Log;

import com.example.ticketservicenew.business.model.BookingInfo;
import com.example.ticketservicenew.business.model.Event;
import com.example.ticketservicenew.business.model.LockedSeats;
import com.example.ticketservicenew.business.model.Seat;
import com.example.ticketservicenew.data.dto.EventBookingDto;
import com.example.ticketservicenew.data.provider.store.StoreProvider;
import com.example.ticketservicenew.data.provider.web.Api;

import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

import io.reactivex.Completable;
import io.reactivex.Single;
import retrofit2.Response;

public class PayingRepositoryImpl implements PayingRepository{
    public static final String TAG = PayingRepositoryImpl.class.getName();
    private Api api;
    private StoreProvider storeProvider;

    String eventId;
    BookingInfo info;

    public PayingRepositoryImpl(Api api, StoreProvider storeProvider) {
        this.api = api;
        this.storeProvider = storeProvider;
    }

    @Override
    public List<LockedSeats> getBookedSeats() {
        return info.getLockedSeats();
    }

    @Override
    public String getEventId() {
        return eventId;
    }

    @Override
    public Completable sellTickets() {
        EventBookingDto request = mapSeatsListToDto(info.getLockedSeats());
        return Completable.fromSingle(api.sellTickets(request));
    }

    private EventBookingDto mapSeatsListToDto(List<LockedSeats> lockedSeats) {
            Map<String, List<String>> bookedSeats = new TreeMap<>();
            for(LockedSeats seats : lockedSeats){
                bookedSeats.put(seats.getRow(), seats.getSeats());
            }
            List<Map<String, List<String>>> list = new ArrayList<>();
            list.add(bookedSeats);
            return new EventBookingDto(eventId, list);
    }

    @Override
    public BookingInfo getBookingInfo(String eventId) {
        if(info != null){
            return info;
        }
        this.eventId = eventId;
        Set<String> tickets = storeProvider.getBookedTickets(eventId);
        BookingInfo info = new BookingInfo();
        for (String ticket : tickets) {
            String[] rowAndSeat = ticket.split(",");
            info.addBookedSeat(rowAndSeat[0], rowAndSeat[1]);
        }
        info.setTotalPrice(storeProvider.getTotalPrice());
        this.info = info;
        return info;
    }

    @Override
    public double getTotalPrice() {
        return info.getTotalPrice();
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
