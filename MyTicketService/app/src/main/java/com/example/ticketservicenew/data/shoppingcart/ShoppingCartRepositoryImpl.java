package com.example.ticketservicenew.data.shoppingcart;

import android.util.Log;

import com.example.ticketservicenew.business.model.Seat;
import com.example.ticketservicenew.data.dto.EventBookingDto;
import com.example.ticketservicenew.data.dto.objectsForDto.LockedSeatsDto;
import com.example.ticketservicenew.data.provider.web.Api;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import io.reactivex.Completable;
import io.reactivex.Single;
import retrofit2.Response;

public class ShoppingCartRepositoryImpl implements ShoppingCartRepository{
    private static final String TAG = ShoppingCartRepositoryImpl.class.getName();
    private Api api;

    String eventId;
    List<Seat> bookedSeats;

    public ShoppingCartRepositoryImpl(Api api) {
        this.api = api;
    }

//    @Override
//    public Completable onBookingCancel() {
//        EventBookingDto dto = mapSeatModelToDto(bookedSeats);
//        for(Map<String, List<String>> rows : dto.lockedSeats){
//            for (Map.Entry<String, List<String>> pair : rows.entrySet()){
//                Log.d(TAG, "on booking cancel: event id: " + dto.eventId + "row: " + pair.getKey() + "seat: " + pair.getValue());
//            }
//        }
//
//        return Completable.fromSingle(api.cancelBooking(dto).flatMap(this::onBookingCancelSuccess))
//                .doOnComplete(() -> bookedSeats.clear());
//                //.doOnError(error -> Log.d(TAG, "on cancel booking error: " + error.getMessage()));
//    }

    @Override
    public Single<Void> onBookingCancel() {
        EventBookingDto dto = mapSeatModelToDto(bookedSeats);
        for(Map<String, List<String>> rows : dto.lockedSeats){
            for (Map.Entry<String, List<String>> pair : rows.entrySet()){
                Log.d(TAG, "on booking cancel: event id: " + dto.eventId + "row: " + pair.getKey() + "seat: " + pair.getValue());
            }
        }

        return api.cancelBooking(dto).flatMap(this::onBookingCancelSuccess);

    }

    @Override
    public void saveId(String eventId) {
        this.eventId = eventId;
    }

    @Override
    public void saveBookedSeats(List<Seat> seats) {
        this.bookedSeats = seats;
    }

    @Override
    public String getId() {
        return eventId;
    }

    @Override
    public List<Seat> getBookedSeats() {
        return bookedSeats;
    }

    private EventBookingDto mapSeatModelToDto(List<Seat> seats){
        //List<LockedSeatsDto> res = new ArrayList<>();
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
//        for (Map.Entry<String, List<String>> pair : map.entrySet()) {
//            res.add(new LockedSeatsDto(pair.getKey(), pair.getValue()));
//        }
        return new EventBookingDto(eventId, lockedSeats);
    }

    private Single<Void> onBookingCancelSuccess(Response<Void> response) throws IOException {
        Log.d(TAG, "onGetEventInfoSuccess: " + response.code());
        if (response.isSuccessful()){
            Log.d(TAG, "onGetEventInfoSuccess: " + response.body());
            return Single.just(response.body());
        }else {
            Log.d(TAG, "onGetEventInfoSuccess: " + response.errorBody().string());
            throw new RuntimeException("Server error! Call to support");
        }
    }
}
