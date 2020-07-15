package com.example.ticketservicenew.business.paymentsuccess;

import com.example.ticketservicenew.business.model.BookingInfo;
import com.example.ticketservicenew.business.model.LockedSeats;
import com.example.ticketservicenew.business.model.Seat;

import java.util.List;

public interface PaymentSuccessInteractor {

    String getEventId();

    List<LockedSeats> getSoldSeats();

    BookingInfo getPaymentInfo(String eventId);

    void clearBookingInfo();
}
