package com.example.ticketservicenew.data.shoppingcart;

import android.util.Log;

import androidx.annotation.Nullable;

import com.example.ticketservicenew.business.model.BookingInfo;
import com.example.ticketservicenew.business.model.Event;
import com.example.ticketservicenew.business.model.LockedSeats;
import com.example.ticketservicenew.business.model.Price;
import com.example.ticketservicenew.business.model.Seat;
import com.example.ticketservicenew.data.dto.EventBookingDto;
import com.example.ticketservicenew.data.dto.HallStructureDto;
import com.example.ticketservicenew.data.dto.admin.EventOutputDto;
import com.example.ticketservicenew.data.dto.admin.PriceRangeDto;
import com.example.ticketservicenew.data.provider.store.StoreProvider;
import com.example.ticketservicenew.data.provider.web.Api;

import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

import io.reactivex.Single;
import retrofit2.Response;
import timber.log.Timber;

public class ShoppingCartRepositoryImpl implements ShoppingCartRepository{
    private static final String TAG = ShoppingCartRepositoryImpl.class.getName();

    private Api api;
    private StoreProvider storeProvider;
    private String eventId;
    private BookingInfo info;

    public ShoppingCartRepositoryImpl(Api api, StoreProvider storeProvider) {
        this.api = api;
        this.storeProvider = storeProvider;
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
    public Single<Event> getEvent() {
        return api.getEvent(eventId).flatMap(this::onGetEventSuccess).map(this::mapEventDtoToModel);
        //return null;
    }

    @Override
    public Single<Void> onBookingCancel() {
       storeProvider.clearBooking();
        EventBookingDto dto = mapSeatModelToDto(info.getLockedSeats());
//        for(Map<String, List<String>> rows : dto.lockedSeats){
//            for (Map.Entry<String, List<String>> pair : rows.entrySet()){
//                Timber.d("on booking cancel: event id: " + dto.eventId + "row: " + pair.getKey() + "seat: " + pair.getValue());
//            }
//        }
        return api.cancelBooking(dto).flatMap(this::onBookingCancelSuccess);
    }

    @Override
    public String getEventId() {
        return eventId;
    }

    @Nullable
    @Override
    public BookingInfo getBookingInfo() {
        if(this.info != null){
            return info;
        }
        String eventId = storeProvider.getEventId();
        if (eventId.isEmpty()) {
            return null;
        }
        Timber.d("event id: " + eventId);
            Set<String> tickets = storeProvider.getBookedTickets(eventId);
            BookingInfo info = new BookingInfo();
            for (String ticket : tickets) {
                Timber.d("ticket: " + ticket);
                String[] rowAndSeat = ticket.split(",");
                info.addBookedSeat(rowAndSeat[0], rowAndSeat[1]);
            }
            info.setTotalPrice(storeProvider.getTotalPrice());
            this.info = info;
            this.eventId = eventId;
            return info;
        }

    private Single<EventOutputDto> onGetEventSuccess(Response<EventOutputDto> response) throws IOException {
        if (response.isSuccessful()){
            Log.d(TAG, "onGetEventSuccess: " + response.body());
            EventOutputDto event = response.body();
            return Single.just(event);
        }else {
            Log.d(TAG, "onGetEventSuccess: " + response.errorBody().string());
            throw new RuntimeException("Server error! Call to support");
        }
    }

    private Event mapEventDtoToModel(EventOutputDto dto){
        return new Event(dto.getEventId(),
                dto.getEventStatus(),
                dto.getEventName(),
                dto.getArtist(),
                dto.getEventStart(),
                dto.getEventDurationHours(),
                dto.getHall(),
                dto.getEventType(),
                dto.getDescription(),
                dto.getImages(),
                dto.getPriceRanges().toString(),
                dto.getManagers().toString());
    }

//    @Override
//    public double getTotalPrice() {
//        return storeProvider.getTotalPrice();
//    }

//    @Override
//    public int getTotalTicketsNum() {
//        return storeProvider.getTotalTicketsNum();
//    }


//    @Override
//    public Single<List<Price>> getPriceList(String eventId) {
//        return api.getHallStructure(eventId, true)
//                .flatMap(this::onGetPriceListSuccess)
//                .map(this::mapHallStructureDtoToModel);
//    }

//    @Override
//    public void saveId(String eventId) {
//        this.eventId = eventId;
//    }

//    @Override
//    public void saveBookedSeats(List<Seat> seats) {
//        this.bookedSeats = seats;
//    }



//    @Override
//    public List<Seat> getBookedSeats() {
//        return bookedSeats;
//    }

    private EventBookingDto mapSeatModelToDto(List<LockedSeats> seats){
        //List<LockedSeatsDto> res = new ArrayList<>();
        Map<String, List<String>> map = new LinkedHashMap<>();
        for(LockedSeats lockedSeats : seats){
           map.put(lockedSeats.getRow(), lockedSeats.getSeats());
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

//    private Single<HallStructureDto> onGetPriceListSuccess(Response<HallStructureDto> response) throws IOException {
//        if (response.isSuccessful()) {
//            Timber.d("onGetHallStructureSuccess: %s", response.body());
//            HallStructureDto hallStructure = response.body();
//            return Single.just(hallStructure);
//        } else {
//            Timber.d("onGetHallStructureSuccess: %s", response.errorBody().string());
//            throw new RuntimeException("Server error! Call to support");
//        }
//    }

//    private List<Price> mapHallStructureDtoToModel(HallStructureDto dto) {
//        //Map<Pair<Double, String>, List<Integer>> priceRanges = new HashMap<>();
//        //Map<Integer, List<String>> lockedSeats = new HashMap<>();
//        //List<Pair<Double, String>> priceList = new ArrayList<>();
//        List<Price> priceList = new ArrayList<>();
//        //List<LockedSeats> lockedSeats = new ArrayList<>();
////        List<LockedSeatsDto> lockedSeatsList = dto.getLockedSeats();
////        if(!lockedSeats.isEmpty()) {
////            for (LockedSeatsDto seats : lockedSeatsList) {
////                lockedSeats.add(new LockedSeats(seats.row, seats.seats));
////            }
////        }
//        List<PriceRangeDto> priceRangesList = dto.getPriceRanges();
//        if (!priceRangesList.isEmpty()) {
//            for (PriceRangeDto priceRange : priceRangesList) {
//                priceList.add(new Price(priceRange.getColor(), priceRange.getPrice(), priceRange.getRows()));
//            }
//        }
//        return priceList;
//    }
}
