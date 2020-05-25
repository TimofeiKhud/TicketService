package com.example.ticketservicenew.data.provider.store;

import android.content.Context;

import androidx.annotation.Nullable;

import java.util.Calendar;
import java.util.Date;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;

public class SprefStoreProvider implements StoreProvider{
    private static final String SP_AUTH = "AUTH";
    //private static final String SP_BOOKING_INFO = "BOOKINGINFO";
    //private static final String SP_BOOKED_TICKETS = "BOOKEDTICKETS";
    private static final String TOKEN_KEY = "TOKEN";
    private static final String USERNAME_KEY = "USERNAME";
    private static final String TIME_KEY = "BOOKINGTIME";
    public static final String PRICE_KEY = "PRICE";
    public static final String EVENT_ID_KEY = "EVENTID";
    public static final String TICKETS_NUM_KEY = "TICKETSNUM";

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
    public boolean saveBookingInfo(String eventId, long time, double price) {
//         Set<String> eventIds = context.getSharedPreferences(SP_BOOKING_INFO, Context.MODE_PRIVATE)
//                .getStringSet(EVENTS_KEY, new TreeSet<>());
//         eventIds.add(eventId);
        String userLogin = context.getSharedPreferences(SP_AUTH, Context.MODE_PRIVATE)
                .getString(USERNAME_KEY, null);
        if (userLogin == null || userLogin.isEmpty()){
            return false;
        }
         double totalPrice = context.getSharedPreferences(userLogin, Context.MODE_PRIVATE)
                 .getFloat(PRICE_KEY, 0);
         totalPrice += price;
         return context.getSharedPreferences(userLogin, Context.MODE_PRIVATE)
                 .edit()
                 .putString(EVENT_ID_KEY, eventId)
                 .commit() &&
                 context.getSharedPreferences(userLogin, Context.MODE_PRIVATE)
                 .edit()
                 .putLong(TIME_KEY, new Date().getTime())
                 .commit() &&
                 context.getSharedPreferences(userLogin, Context.MODE_PRIVATE)
                 .edit()
                 .putFloat(PRICE_KEY, (float)totalPrice)
                 .commit();

    }

    @Nullable
    @Override
    public Set<String> getBookedTickets(String eventId) {
        checkExpiredBooking();

//        return context.getSharedPreferences(SP_BOOKED_TICKETS, Context.MODE_PRIVATE)
//                .getStringSet(eventId, null);

        String userLogin = context.getSharedPreferences(SP_AUTH, Context.MODE_PRIVATE)
                .getString(USERNAME_KEY, null);
        if (userLogin == null || userLogin.isEmpty()){
            return null;
        }
        return context.getSharedPreferences(userLogin, Context.MODE_PRIVATE)
                .getStringSet(eventId, null);
    }

    @Override
    public String getEventId() {
        checkExpiredBooking();

        String userLogin = context.getSharedPreferences(SP_AUTH, Context.MODE_PRIVATE)
                .getString(USERNAME_KEY, null);
        if (userLogin == null || userLogin.isEmpty()){
            return null;
        }
        return context.getSharedPreferences(userLogin, Context.MODE_PRIVATE)
                .getString(EVENT_ID_KEY, "");
    }

    @Override
    public double getTotalPrice() {
        String userLogin = context.getSharedPreferences(SP_AUTH, Context.MODE_PRIVATE)
                .getString(USERNAME_KEY, null);
        if (userLogin == null || userLogin.isEmpty()){
            return 0;
        }
        return context.getSharedPreferences(userLogin, Context.MODE_PRIVATE)
                .getFloat(PRICE_KEY, 0);
    }

    @Override
    public int getTotalTicketsNum() {
        String userLogin = context.getSharedPreferences(SP_AUTH, Context.MODE_PRIVATE)
                .getString(USERNAME_KEY, null);
        if (userLogin == null || userLogin.isEmpty()){
            return 0;
        }
        return context.getSharedPreferences(userLogin, Context.MODE_PRIVATE)
                .getInt(TICKETS_NUM_KEY, 0);
    }

    @Override
    public boolean saveBookedTickets(String eventId, Set<String> seats) {
        String userLogin = context.getSharedPreferences(SP_AUTH, Context.MODE_PRIVATE)
                .getString(USERNAME_KEY, null);
        if (userLogin == null || userLogin.isEmpty()){
            return false;
        }
        int bookedTicketsNum = context.getSharedPreferences(userLogin, Context.MODE_PRIVATE)
                .getInt(TICKETS_NUM_KEY, 0);
        bookedTicketsNum += seats.size();
        return context.getSharedPreferences(userLogin, Context.MODE_PRIVATE)
                .edit()
                .putStringSet(eventId, seats)
                .commit() &&
                context.getSharedPreferences(userLogin, Context.MODE_PRIVATE)
                .edit()
                .putInt(TICKETS_NUM_KEY, bookedTicketsNum)
                .commit();
    }



//    @Nullable
//    @Override
//    public Map<String, Set<String>> getBookedTickets() {
//        Set<String> eventIds = context.getSharedPreferences(SP_BOOKING_INFO, Context.MODE_PRIVATE)
//                .getStringSet(EVENT_ID_KEY, null);
//        if (eventIds == null){
//            return null;
//        }
//        Map<String, Set<String>> res = new TreeMap<>();
//        for(String id : eventIds){
//            Set<String> tickets = context.getSharedPreferences(SP_BOOKED_TICKETS, Context.MODE_PRIVATE)
//                    .getStringSet(id, new TreeSet<>());
//            res.put(id, tickets);
//        }
//        return res;
//    }

    //clear booked tickets & booking info
    @Override
    public boolean clearBooking() {
        String userLogin = context.getSharedPreferences(SP_AUTH, Context.MODE_PRIVATE)
                .getString(USERNAME_KEY, null);
        if (userLogin == null || userLogin.isEmpty()){
            return false;
        }
        return context.getSharedPreferences(userLogin, Context.MODE_PRIVATE)
                .edit()
                .clear()
                .commit() &&
                context.getSharedPreferences(userLogin, Context.MODE_PRIVATE)
                .edit()
                .clear()
                .commit();
    }

    private long getBookingTime() {
        String userLogin = context.getSharedPreferences(SP_AUTH, Context.MODE_PRIVATE)
                .getString(USERNAME_KEY, null);
        if (userLogin == null || userLogin.isEmpty()){
            return 0;
        }
        return context.getSharedPreferences(userLogin, Context.MODE_PRIVATE)
                .getLong(TIME_KEY, 0);
    }

    private void checkExpiredBooking(){
        long bookingTime = getBookingTime();
        Calendar cal = Calendar.getInstance();
        cal.setTime(new Date(bookingTime));
        cal.add(Calendar.MINUTE, 10);
        if (new Date().getTime() > cal.getTimeInMillis()) {
            clearBooking();
        }
    }


}
