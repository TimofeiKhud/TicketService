package com.example.ticketservicenew.data.hall;

import android.util.Log;
import android.util.Pair;

import com.example.ticketservicenew.business.model.HallStructure;
import com.example.ticketservicenew.business.model.LockedSeats;
import com.example.ticketservicenew.data.dto.EventBookingDto;
import com.example.ticketservicenew.data.dto.HallStructureDto;
import com.example.ticketservicenew.data.dto.StatusDto;
import com.example.ticketservicenew.data.dto.admin.PriceRangeDto;
import com.example.ticketservicenew.data.dto.objectsForDto.LockedSeatsDto;
import com.example.ticketservicenew.data.dto.objectsForDto.LockedSeatsRequestDto;
import com.example.ticketservicenew.data.provider.store.StoreProvider;
import com.example.ticketservicenew.data.provider.web.Api;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import io.reactivex.Completable;
import io.reactivex.Single;
import retrofit2.Response;

public class HallRepositoryImpl implements HallRepository{
    public static final String TAG = HallRepositoryImpl.class.getName();

    private Api api;
    private StoreProvider storeProvider;
    private String eventId;

    public HallRepositoryImpl(Api api, StoreProvider storeProvider) {
        this.api = api;
        this.storeProvider = storeProvider;
    }

    @Override
    public Single<HallStructure> getHallStructure(String id, boolean isShort) {
        eventId = id;
        return api.getHallStructure(id, isShort).flatMap(this::onGetHallStructureSuccess).map(this::mapHallStructureDtoToModel);
    }

    @Override
    public Completable onSeatsBooked(Map<String, List<String>> seats) {
        Log.d(TAG, "onSeatsBooked");
        for(Map.Entry<String, List<String>> pair : seats.entrySet()){
            for(String seat : pair.getValue()){
                Log.d(TAG, "on seats booked: id: " + eventId + "row: " + pair.getKey() + "seat: " + seat);
            }
        }
        List<Map<String, List<String>>> list = new ArrayList<>();
        list.add(seats);

        EventBookingDto request = new EventBookingDto(eventId, list);
        //return Completable.fromSingle(api.bookSeats(request).doOnSuccess(this::onSeatsBookedSuccess).doOnError(error->{Log.d(TAG, error.getMessage());}));
        return Completable.fromSingle(api.bookSeats(request)).doOnError(error->{Log.d(TAG, error.getMessage());});
    }

    private Single<HallStructureDto> onGetHallStructureSuccess(Response<HallStructureDto> response) throws IOException {
        if (response.isSuccessful()){
            Log.d(TAG, "onGetHallStructureSuccess: " + response.body());
            HallStructureDto hallStructure = response.body();
            return Single.just(hallStructure);
        }else {
            Log.d(TAG, "onGetHallStructureSuccess: " + response.errorBody().string());
            throw new RuntimeException("Server error! Call to support");
        }
    }

    private HallStructure mapHallStructureDtoToModel(HallStructureDto dto){
        Map<Pair<Double, String>, List<Integer>> priceRanges = new HashMap<>();
        Map<Integer, List<String>> lockedSeats = new HashMap<>();
        List<Pair<Double,String>> priceList = new ArrayList<>();
        List<LockedSeatsDto> lockedSeatsList = dto.getLockedSeats();
        if(!lockedSeatsList.isEmpty()){
            for(LockedSeatsDto seats : lockedSeatsList){
                lockedSeats.put(Integer.parseInt(seats.row), seats.seats);
            }
        }
        List<PriceRangeDto> priceRangesList = dto.getPriceRanges();
        if(!priceRangesList.isEmpty()){
            for(PriceRangeDto priceRange : priceRangesList){
                List<Integer> rows = new ArrayList<>();
                Pair<Double, String> price = new Pair<>(priceRange.price, priceRange.color);
                for(String row : priceRange.rows){
                    rows.add(Integer.parseInt(row));
                }
                priceRanges.put(price, rows);
                priceList.add(price);
            }
        }
        return new HallStructure(priceRanges, lockedSeats, priceList);
    }



    public String getEventId() {
        return eventId;
    }
}
