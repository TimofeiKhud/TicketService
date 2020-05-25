package com.example.ticketservicenew.business.paymentsuccess;

import com.example.ticketservicenew.business.model.BookingInfo;
import com.example.ticketservicenew.business.model.LockedSeats;
import com.example.ticketservicenew.business.model.Seat;

import java.util.List;

public interface PaymentSuccessInteractor {

    String getEventId();

    List<LockedSeats> getSoldSeats();

    //void saveId(String eventId);

    //void saveBookedSeats(List<Seat> seats);

    BookingInfo getPaymentInfo(String eventId);

    void clearBookingInfo();
}
