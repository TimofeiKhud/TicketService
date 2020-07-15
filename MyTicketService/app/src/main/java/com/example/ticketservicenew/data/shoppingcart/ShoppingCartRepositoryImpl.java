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

    @Override
    public Single<Event> getEvent() {
        return api.getEvent(eventId).flatMap(this::onGetEventSuccess).map(this::mapEventDtoToModel);
    }

    @Override
    public Single<Void> onBookingCancel() {
       storeProvider.clearBooking();
        EventBookingDto dto = mapSeatModelToDto(info.getLockedSeats());
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

    private EventBookingDto mapSeatModelToDto(List<LockedSeats> seats){
        //List<LockedSeatsDto> res = new ArrayList<>();
        Map<String, List<String>> map = new LinkedHashMap<>();
        for(LockedSeats lockedSeats : seats){
           map.put(lockedSeats.getRow(), lockedSeats.getSeats());
        }
        List<Map<String, List<String>>> lockedSeats = new ArrayList<>();
        lockedSeats.add(map);
        return new EventBookingDto(eventId, lockedSeats);
    }

    private Single<Void> onBookingCancelSuccess(Response<Void> response) throws IOException {
        if (response.isSuccessful()){
            return Single.just(response.body());
        }else {
            throw new RuntimeException("Server error! Call to support");
        }
    }
}
