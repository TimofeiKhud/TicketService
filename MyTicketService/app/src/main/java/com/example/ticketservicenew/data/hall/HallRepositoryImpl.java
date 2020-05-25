package com.example.ticketservicenew.data.hall;

import androidx.annotation.Nullable;

import com.example.ticketservicenew.business.model.BookingInfo;
import com.example.ticketservicenew.business.model.HallStructure;
import com.example.ticketservicenew.business.model.LockedSeats;
import com.example.ticketservicenew.business.model.Price;
import com.example.ticketservicenew.business.model.Seat;
import com.example.ticketservicenew.data.dto.EventBookingDto;
import com.example.ticketservicenew.data.dto.HallStructureDto;
import com.example.ticketservicenew.data.dto.admin.PriceRangeDto;
import com.example.ticketservicenew.data.dto.objectsForDto.LockedSeatsDto;
import com.example.ticketservicenew.data.provider.store.StoreProvider;
import com.example.ticketservicenew.data.provider.web.Api;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
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
    private List<Price> priceList;

    public HallRepositoryImpl(Api api, StoreProvider storeProvider) {
        this.api = api;
        this.storeProvider = storeProvider;
    }

    @Override
    public Single<HallStructure> getHallStructure(String id) {
        return api.getHallStructure(id, false)
                .flatMap(this::onGetHallStructureSuccess)
                .map(this::mapHallStructureDtoToModel);
    }

    @Override
    public Completable onSeatsBooking(List<LockedSeats> seats) {
        EventBookingDto request = mapSeatsListToDto(seats);
        //return Completable.fromSingle(api.bookSeats(request).doOnSuccess(this::onSeatsBookedSuccess).doOnError(error->{Log.d(TAG, error.getMessage());}));
        return Completable.fromSingle(api.bookSeats(request));
//                .doOnComplete(() -> {
//                    double totalPrice = 0;
//                    for(Seat seat : seats){
//                        totalPrice += seat.getPrice();
//                    }
//                    storeProvider.saveBookingInfo(eventId, new Date().getTime(), totalPrice);
//                    storeProvider.saveBookedTickets(eventId, mapModelToStoreProviderObj(seats));
//                })
//                .doOnError(error -> {
//                    Timber.d(error.getMessage());
//                });
    }

    @Override
    public String getCurrentBookingEventId() {
        return storeProvider.getEventId();
    }

    @Nullable
    @Override
    public BookingInfo getCurrentBookingInfo(String eventId) {
        Set<String> tickets = storeProvider.getBookedTickets(eventId);
        if(tickets == null){
            return null;
        }
        BookingInfo info = new BookingInfo();
        for (String ticket : tickets) {
            String[] rowAndSeat = ticket.split(",");
            info.addBookedSeat(rowAndSeat[0], rowAndSeat[1]);
        }
        info.setTotalPrice(storeProvider.getTotalPrice());
        return info;
    }

    @Override
    public boolean saveBookingInfo(BookingInfo bookingInfo) {
        String savedEventId = storeProvider.getEventId();
        if(eventId.equals(savedEventId)){
            Set<String> savedSeats = storeProvider.getBookedTickets(savedEventId);
            savedSeats.addAll(mapModelToStoreProviderObj(bookingInfo.getLockedSeats()));
            return storeProvider.saveBookingInfo(eventId, new Date().getTime(), bookingInfo.getTotalPrice()) &&
            storeProvider.saveBookedTickets(eventId, savedSeats);
        }else{
            return storeProvider.clearBooking()&&
            storeProvider.saveBookingInfo(eventId, new Date().getTime(), bookingInfo.getTotalPrice())&&
            storeProvider.saveBookedTickets(eventId, mapModelToStoreProviderObj(bookingInfo.getLockedSeats()));
        }

    }

    @Override
    public void saveEventId(String eventId) {
        this.eventId = eventId;
    }

//    @Nullable
//    @Override
//    public BookingInfo getCurrentBookingInfo(String eventId) {
//        Set<String> bookedTickets = storeProvider.getBookedTickets(eventId);
//        if(bookedTickets == null){
//            return null;
//        }
//        List<LockedSeats> lockedSeats = new ArrayList<>();
//
//    }

//    @Override
//    public Map<String, List<String>> getBookedSeats(String eventId) {
//        if(storeProvider.getBookingTime(eventId) == StoreProvider.TIME_NOT_SET){
//            return null;
//        }
//        Calendar calendar = Calendar.getInstance();
//        calendar.setTime(new Date(storeProvider.getBookingTime(eventId)));
//        calendar.add(Calendar.MINUTE, 10);
//        long currentTime = new Date().getTime();
//        if(currentTime > calendar.getTimeInMillis()){
//            storeProvider.clearBookingTime();
//            storeProvider.clearBookingInfo();
//            return null;
//        }else{
//            return mapStringToSeats(storeProvider.getBookingInfo(eventId));
//        }
//    }

//    @Override
//    public void saveEventId(String eventId) {
//        this.eventId = eventId;
//    }

//    @Override
//    public void saveHallStructure(HallStructure hallStructure) {
//        this.hallStructure = hallStructure;
//    }

    @Override
    public List<Price> getPriceList() {
        return priceList;
    }

    @Override
    public void savePriceList(List<Price> priceList) {
        this.priceList = priceList;
    }

    @Override
    public String getEventId() {
        return eventId;
    }

    @Override
    public String getToken() {
        return storeProvider.getToken();
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

    private EventBookingDto mapSeatsListToDto(List<LockedSeats> seats) {
        Map<String, List<String>> bookedSeats = new TreeMap<>();
        for(LockedSeats lockedSeats : seats){
            bookedSeats.put(lockedSeats.getRow(), lockedSeats.getSeats());
        }
//        for(Seat seat : seats){
//            Timber.d("hallInteractor, seat to book:%s", seat.toString());
//            if(bookedSeats.containsKey(seat.getRow())){
//                List<String> seatList = bookedSeats.get(seat.getRow());
//                //if(!seatList.contains(seat.getSeatNum())) {
//                seatList.add(seat.getSeatNum());
//                //}
//                bookedSeats.put(seat.getRow(), seatList);
//            }else{
//                List<String> list = new ArrayList<>();
//                list.add(seat.getSeatNum());
//                bookedSeats.put(seat.getRow(), list);
//            }
//        }
        List<Map<String, List<String>>> list = new ArrayList<>();
        list.add(bookedSeats);
        return new EventBookingDto(eventId, list);
    }

    private HallStructure mapHallStructureDtoToModel(HallStructureDto dto) {
        //Map<Pair<Double, String>, List<Integer>> priceRanges = new HashMap<>();
        //Map<Integer, List<String>> lockedSeats = new HashMap<>();
        //List<Pair<Double, String>> priceList = new ArrayList<>();
        List<Price> priceList = new ArrayList<>();
        List<LockedSeats> lockedSeats = new ArrayList<>();
        List<LockedSeatsDto> lockedSeatsList = dto.getLockedSeats();
        if(!lockedSeats.isEmpty()) {
            for (LockedSeatsDto seats : lockedSeatsList) {
                lockedSeats.add(new LockedSeats(seats.row, seats.seats));
            }
        }
        List<PriceRangeDto> priceRangesList = dto.getPriceRanges();
        if (!priceRangesList.isEmpty()) {
            for (PriceRangeDto priceRange : priceRangesList) {
               priceList.add(new Price(priceRange.getColor(), priceRange.getPrice(), priceRange.getRows()));
            }
        }
        return new HallStructure(priceList, lockedSeats);
    }

    private Set<String> mapModelToStoreProviderObj(List<LockedSeats> seats) {
        Set<String> res = new TreeSet<>();
        for (LockedSeats seatList : seats) {
            for(String seat : seatList.getSeats()){
                res.add(seatList.getRow() + "," + seat);
            }
        }
        return res;
    }

//    private Map<String, List<String>> mapStoreProviderObjToModel(Set<String> set){
//        Map<String, List<String>> res = new TreeMap<>();
//        for(String s : set){
//            String[] seat = s.split(",");
//
//                if(res.containsKey(seat[0])){
//                    List<String> seatList = res.get(seat[0]);
//                    seatList.add(seat[1]);
//                    res.put(seat[0], seatList);
//                }else{
//                    List<String> list = new ArrayList<>();
//                    list.add(seat[1]);
//                    res.put(seat[0], list);
//                }
//            }
//        return res;
//        }


}
