package com.example.ticketservicenew.data.provider.store;

import android.content.Context;
import java.util.Set;

public class SprefStoreProvider implements StoreProvider{
    private static final String SP_AUTH = "AUTH";
    private static final String SP_BOOKING_TIME = "BOOKINGTIME";
    private static final String SP_BOOKING_INFO = "BOOKINGINFO";
    private static final String TOKEN_KEY = "TOKEN";
    private static final String USERNAME_KEY = "USERNAME";

    private Context context;

    public SprefStoreProvider(Context context) {
        this.context = context;
    }

    @Override
    public boolean saveToken(String token) {
        return context.getSharedPreferences(SP_AUTH, Context.MODE_PRIVATE)
                .edit()
                .putString(TOKEN_KEY, token)
                .commit();
    }

    @Override
    public String getToken() {
        return context.getSharedPreferences(SP_AUTH, Context.MODE_PRIVATE)
                .getString(TOKEN_KEY, null);
    }

    @Override
    public boolean clearToken() {
        return context.getSharedPreferences(SP_AUTH, Context.MODE_PRIVATE)
                .edit()
                .remove(TOKEN_KEY)
                .commit();
    }

    @Override
    public boolean saveUserName(String userName) {
        return context.getSharedPreferences(SP_AUTH, Context.MODE_PRIVATE)
                .edit()
                .putString(USERNAME_KEY, userName)
                .commit();
    }

    @Override
    public String getUserName() {
        return context.getSharedPreferences(SP_AUTH, Context.MODE_PRIVATE)
                .getString(USERNAME_KEY, null);
    }

    @Override
    public boolean clearUserName() {
        return context.getSharedPreferences(SP_AUTH, Context.MODE_PRIVATE)
                .edit()
                .remove(USERNAME_KEY)
                .commit();
    }

    @Override
    public boolean saveBookingTime(String eventId, long time) {
        return context.getSharedPreferences(SP_BOOKING_TIME, Context.MODE_PRIVATE)
                .edit()
                .putLong(eventId, time)
                .commit();
    }

    @Override
    public long getBookingTime(String eventId) {
        return context.getSharedPreferences(SP_BOOKING_TIME, Context.MODE_PRIVATE)
                .getLong(eventId, TIME_NOT_SET);
    }

    @Override
    public boolean clearBookingTime() {
        return context.getSharedPreferences(SP_BOOKING_TIME, Context.MODE_PRIVATE)
                .edit()
                .clear()
                .commit();
    }

    @Override
    public boolean saveBookingInfo(String eventId, Set<String> seats) {
        return context.getSharedPreferences(SP_BOOKING_INFO, Context.MODE_PRIVATE)
                .edit()
                .putStringSet(eventId, seats)
                .commit();
    }

    @Override
    public Set<String> getBookingInfo(String eventId) {
        return context.getSharedPreferences(SP_BOOKING_INFO, Context.MODE_PRIVATE)
                .getStringSet(eventId, null);
    }

    @Override
    public boolean clearBookingInfo() {
        return context.getSharedPreferences(SP_BOOKING_INFO, Context.MODE_PRIVATE)
                .edit()
                .clear()
                .commit();
    }
}
