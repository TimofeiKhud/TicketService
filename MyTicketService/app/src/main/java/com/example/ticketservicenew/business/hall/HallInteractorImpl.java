package com.example.ticketservicenew.business.hall;

import com.example.ticketservicenew.business.model.BookingInfo;
import com.example.ticketservicenew.business.model.HallStructure;
import com.example.ticketservicenew.business.model.LockedSeats;
import com.example.ticketservicenew.business.model.Price;
import com.example.ticketservicenew.business.model.Seat;
import com.example.ticketservicenew.data.hall.HallRepository;


import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.locks.Lock;

import io.reactivex.Completable;
import io.reactivex.Single;

public class HallInteractorImpl implements HallInteractor{
    private static final String TAG = HallInteractorImpl.class.getName();
    private static final int MAX_SEATS_TO_BOOK = 10;
    private HallRepository repository;
    private List<LockedSeats> lockedSeatsList;

    public HallInteractorImpl(HallRepository repository) {
        this.repository = repository;
    }

    @Override
    public Single<HallStructure> getHallStructure(String id) {
        return repository.getHallStructure(id);
    }

    @Override
    public Completable onSeatsBooking(List<Seat> seats) {
        try{
            isUserLogin();
            isEmptyBooking(seats);
            isShoppingCartContainsTickets(repository.getEventId());
            lockedSeatsList = new ArrayList<>();
            for(Seat seat : seats){
                boolean seatAdded = false;
                for(LockedSeats lockedSeats : lockedSeatsList){
                    if (seat.getRow().equals(lockedSeats.getRow())){
                        lockedSeats.addSeat(seat.getSeatNum());
                        seatAdded = true;
                        break;
                    }
                }
                if(!seatAdded){
                    List<String> seatList = new ArrayList<>();
                    seatList.add(seat.getSeatNum());
                    lockedSeatsList.add(new LockedSeats(seat.getRow(), seatList));
                }
            }
            return repository.onSeatsBooking(lockedSeatsList);
        }catch (Throwable throwable){
            return Completable.error(throwable);
        }
    }

    @Override
    public String getEventId() {
        return repository.getEventId();
    }

    @Override
    public BookingInfo getCurrentBookingInfo(String eventId) {
        return repository.getCurrentBookingInfo(eventId);
    }

    @Override
    public void onBookingSuccess() {
        double totalPrice = getTotalPrice(lockedSeatsList, repository.getPriceList());
        BookingInfo info = new BookingInfo(lockedSeatsList, totalPrice);
        repository.saveBookingInfo(info);
    }

    @Override
    public void saveId(String eventId) {
        repository.saveEventId(eventId);
    }

    @Override
    public void savePriceList(List<Price> priceList) {
        repository.savePriceList(priceList);
    }

    @Override
    public void onSeatSelected(int selectedSeatsNum) throws MaxSeatsSelectedException{
        if (selectedSeatsNum >= MAX_SEATS_TO_BOOK){
            throw new MaxSeatsSelectedException(String.format("Maximum number of tickets to book is %d", MAX_SEATS_TO_BOOK));
        }
    }

    //throw exception if shopping cart contains tickets for another event
    private void isShoppingCartContainsTickets(String eventId){
        String id = repository.getCurrentBookingEventId();
        if (id.isEmpty() || id.equals(eventId)){
        return;
        }
        else{
            throw new ShoppingCartFilledException("Proceed Main Menu -> Shopping Cart to pay or cancel your previous booking");
        }
    }

    private void isUserLogin(){
        String token = repository.getToken();
        if(token == null || token.isEmpty()){
            throw new UnregisteredUserException("Please login or register new account");
        }
    }

    private void isEmptyBooking(List<Seat> seats){
        if(seats.isEmpty()){
            throw new NoSeatsToBookException("Please select seats for booking");
        }
    }

        private double getTotalPrice(List<LockedSeats> lockedSeats, List<Price> priceList){
        double totalPrice = 0;
            for(LockedSeats seats : lockedSeats){
                boolean priceFound = false;
                for(Price price : priceList) {
                    if(priceFound){
                        break;
                    }
                    for(String row : price.getRows()) {
                        if (row.equals(seats.getRow())) {
                            priceFound = true;
                            totalPrice += price.getPrice() * seats.getSeats().size();
                            break;
                        }
                    }
                }
            }
        return totalPrice;
    }
}
