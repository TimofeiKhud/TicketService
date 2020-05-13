package com.example.ticketservicenew.data.hall;

import android.util.Log;
import android.util.Pair;

import com.example.ticketservicenew.business.model.HallStructure;
import com.example.ticketservicenew.business.model.Seat;
import com.example.ticketservicenew.data.dto.EventBookingDto;
import com.example.ticketservicenew.data.dto.HallStructureDto;
import com.example.ticketservicenew.data.dto.admin.PriceRangeDto;
import com.example.ticketservicenew.data.dto.objectsForDto.LockedSeatsDto;
import com.example.ticketservicenew.data.provider.store.StoreProvider;
import com.example.ticketservicenew.data.provider.web.Api;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;

import io.reactivex.Completable;
import io.reactivex.Single;
import retrofit2.Response;
import timber.log.Timber;

public class HallRepositoryImpl implements HallRepository {
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
    public Completable onSeatsBooking(List<Seat> seats) {
        EventBookingDto request = mapSeatsListToDto(seats);
        //return Completable.fromSingle(api.bookSeats(request).doOnSuccess(this::onSeatsBookedSuccess).doOnError(error->{Log.d(TAG, error.getMessage());}));
        return Completable.fromSingle(api.bookSeats(request))
                .doOnComplete(() -> {
                    storeProvider.saveBookingTime(eventId, new Date().getTime());
                    storeProvider.saveBookingInfo(eventId, mapSeatsToString(seats));
                })
                .doOnError(error -> {
                    Timber.d(error.getMessage());
                });
    }

    @Override
    public String getEventId() {
        return eventId;
    }

    @Override
    public Map<String, List<String>> getBookedSeats(String eventId) {
        if(storeProvider.getBookingTime(eventId) == StoreProvider.TIME_NOT_SET){
            return null;
        }
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date(storeProvider.getBookingTime(eventId)));
        calendar.add(Calendar.MINUTE, 10);
        long currentTime = new Date().getTime();
        if(currentTime > calendar.getTimeInMillis()){
            storeProvider.clearBookingTime();
            storeProvider.clearBookingInfo();
            return null;
        }else{
            return mapStringToSeats(storeProvider.getBookingInfo(eventId));
        }
    }

    private EventBookingDto mapSeatsListToDto(List<Seat> seats) {
        Map<String, List<String>> bookedSeats = new TreeMap<>();
        for(Seat seat : seats){
            Timber.d("hallInteractor, seat to book:%s", seat.toString());
            if(bookedSeats.containsKey(seat.getRow())){
                List<String> seatList = bookedSeats.get(seat.getRow());
                //if(!seatList.contains(seat.getSeatNum())) {
                    seatList.add(seat.getSeatNum());
                //}
                bookedSeats.put(seat.getRow(), seatList);
            }else{
                List<String> list = new ArrayList<>();
                list.add(seat.getSeatNum());
                bookedSeats.put(seat.getRow(), list);
            }
        }
        List<Map<String, List<String>>> list = new ArrayList<>();
        list.add(bookedSeats);
        return new EventBookingDto(eventId, list);
    }

    private Single<HallStructureDto> onGetHallStructureSuccess(Response<HallStructureDto> response) throws IOException {
        if (response.isSuccessful()) {
            Timber.d("onGetHallStructureSuccess: %s", response.body());
            HallStructureDto hallStructure = response.body();
            return Single.just(hallStructure);
        } else {
            Timber.d("onGetHallStructureSuccess: %s", response.errorBody().string());
            throw new RuntimeException("Server error! Call to support");
        }
    }

    private HallStructure mapHallStructureDtoToModel(HallStructureDto dto) {
        Map<Pair<Double, String>, List<Integer>> priceRanges = new HashMap<>();
        Map<Integer, List<String>> lockedSeats = new HashMap<>();
        List<Pair<Double, String>> priceList = new ArrayList<>();
        List<LockedSeatsDto> lockedSeatsList = dto.getLockedSeats();
        if (!lockedSeatsList.isEmpty()) {
            for (LockedSeatsDto seats : lockedSeatsList) {
                lockedSeats.put(Integer.parseInt(seats.row), seats.seats);
            }
        }
        List<PriceRangeDto> priceRangesList = dto.getPriceRanges();
        if (!priceRangesList.isEmpty()) {
            for (PriceRangeDto priceRange : priceRangesList) {
                List<Integer> rows = new ArrayList<>();
                Pair<Double, String> price = new Pair<>(priceRange.price, priceRange.color);
                for (String row : priceRange.rows) {
                    rows.add(Integer.parseInt(row));
                }
                priceRanges.put(price, rows);
                priceList.add(price);
            }
        }
        return new HallStructure(priceRanges, lockedSeats, priceList);
    }

    private Set<String> mapSeatsToString(List<Seat> seats) {
        Set<String> res = new TreeSet<>();
        for (Seat seat : seats) {
            res.add(seat.getRow() + "," + seat.getSeatNum());
        }
        return res;
    }

    private Map<String, List<String>> mapStringToSeats(Set<String> set){
        Map<String, List<String>> res = new TreeMap<>();
        for(String s : set){
            String[] seat = s.split(",");

                if(res.containsKey(seat[0])){
                    List<String> seatList = res.get(seat[0]);
                    seatList.add(seat[1]);
                    res.put(seat[0], seatList);
                }else{
                    List<String> list = new ArrayList<>();
                    list.add(seat[1]);
                    res.put(seat[0], list);
                }
            }
        return res;
        }


}
