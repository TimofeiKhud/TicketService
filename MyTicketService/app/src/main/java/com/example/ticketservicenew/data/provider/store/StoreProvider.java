package com.example.ticketservicenew.data.provider.store;

import com.example.ticketservicenew.business.model.Seat;

import java.util.List;
import java.util.Map;
import java.util.Set;

public interface StoreProvider {
     int TIME_NOT_SET = -1;

    boolean saveToken(String token);
    String getToken();
    boolean clearToken();

    boolean saveUserName(String userName);
    String getUserName();
    boolean clearUserName();

    boolean saveBookingInfo(String eventId, long time, double price);
    String getEventId();
    double getTotalPrice();
    int getTotalTicketsNum();


    boolean saveBookedTickets(String eventId, Set<String> seats);
    Set<String> getBookedTickets(String eventId);

    boolean clearBooking();

}
