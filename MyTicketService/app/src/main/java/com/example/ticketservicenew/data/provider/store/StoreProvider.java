package com.example.ticketservicenew.data.provider.store;

import com.example.ticketservicenew.business.model.Seat;

import java.util.List;
import java.util.Set;

public interface StoreProvider {
    public static final int TIME_NOT_SET = -1;

    boolean saveToken(String token);
    String getToken();
    boolean clearToken();

    boolean saveUserName(String userName);
    String getUserName();
    boolean clearUserName();

    boolean saveBookingTime(String eventId, long time);
    long getBookingTime(String eventId);
    boolean clearBookingTime();

    boolean saveBookingInfo(String eventId, Set<String> seats);
    Set<String> getBookingInfo(String eventId);
    boolean clearBookingInfo();
}
