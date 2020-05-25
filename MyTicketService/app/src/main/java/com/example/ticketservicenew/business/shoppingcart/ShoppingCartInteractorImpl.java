package com.example.ticketservicenew.business.shoppingcart;

import com.example.ticketservicenew.business.model.BookingInfo;
import com.example.ticketservicenew.business.model.Event;
import com.example.ticketservicenew.business.model.Seat;
import com.example.ticketservicenew.data.shoppingcart.ShoppingCartRepository;

import java.util.List;

import io.reactivex.Completable;
import io.reactivex.Single;

public class ShoppingCartInteractorImpl implements ShoppingCartInteractor{
    private static final String TAG = ShoppingCartInteractorImpl.class.getName();
ShoppingCartRepository repository;

    public ShoppingCartInteractorImpl(ShoppingCartRepository repository) {
        this.repository = repository;
    }

    @Override
    public Completable onBookingCancel() {
        return Completable.fromSingle(repository.onBookingCancel());
    }

    @Override
    public BookingInfo getBookingInfo() {
        return repository.getBookingInfo();
    }

    @Override
    public String getEventId() {
        return repository.getEventId();
    }

    @Override
    public Single<Event> getEvent() {
        return repository.getEvent();
    }

//    @Override
//    public int getNumTicketsBooked() {
////        int totalNum = 0;
////        for(BookingInfo info : repository.getBookingInfo()){
////            totalNum += info.getNumTicketsBooked();
////        }
////        return totalNum;
//        return  repository.getTotalTicketsNum();
//    }

//    @Override
//    public double getTotalPrice() {
//        return repository.getTotalPrice();
//    }

//    @Override
//    public void saveId(String eventId) {
//        repository.saveId(eventId);
//    }
//
//    @Override
//    public void saveBookedSeats(List<Seat> seats) {
//        repository.saveBookedSeats(seats);
//    }
//
//    @Override
//    public String getEventId() {
//        return repository.getId();
//    }
//
//    @Override
//    public List<Seat> getBookedSeats() {
//        return repository.getBookedSeats();
//    }
}
