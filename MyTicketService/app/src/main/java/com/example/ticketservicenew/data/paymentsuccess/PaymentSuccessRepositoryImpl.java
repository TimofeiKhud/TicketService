package com.example.ticketservicenew.data.paymentsuccess;

import com.example.ticketservicenew.business.model.BookingInfo;
import com.example.ticketservicenew.business.model.LockedSeats;
import com.example.ticketservicenew.business.model.Seat;
import com.example.ticketservicenew.data.provider.store.StoreProvider;
import com.example.ticketservicenew.data.provider.web.Api;

import java.util.List;
import java.util.Set;

public class PaymentSuccessRepositoryImpl implements PaymentSuccesRepository{
    public static final String TAG = PaymentSuccessRepositoryImpl.class.getName();
    private Api api;
    private StoreProvider storeProvider;

    String eventId;
    BookingInfo info;

    public PaymentSuccessRepositoryImpl(Api api, StoreProvider storeProvider) {
        this.api = api;
        this.storeProvider = storeProvider;
    }

    @Override
    public void clearBookingInfo() {
        storeProvider.clearBooking();
    }

    @Override
    public List<LockedSeats> getSoldSeats() {
        return info.getLockedSeats();
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
    public BookingInfo getPaymentInfo(String eventId) {
        if(info != null){
            return info;
        }else{
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
    }
}
