package com.example.ticketservicenew.data.paymentsuccess;

import com.example.ticketservicenew.business.model.BookingInfo;
import com.example.ticketservicenew.business.model.LockedSeats;
import com.example.ticketservicenew.business.model.Seat;

import java.util.List;

public interface PaymentSuccesRepository {
    String getEventId();

    BookingInfo getPaymentInfo(String eventId);

    void clearBookingInfo();

    List<LockedSeats> getSoldSeats();

    void saveId(String eventId);

    //void saveSoldSeats(List<LockedSeats> seats);
}
